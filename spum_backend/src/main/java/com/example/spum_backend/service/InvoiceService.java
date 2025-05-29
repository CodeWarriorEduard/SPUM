package com.example.spum_backend.service;

import com.example.spum_backend.dto.response.FileInfoResponseDTO;
import com.example.spum_backend.entity.Booking;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class InvoiceService {

    private final SpringTemplateEngine templateEngine;

    public InvoiceService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // Booking template


    private String bookingInvoiceTemplate(Booking booking){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);

        Context context = new Context();
        Map<String, Object> templateVariables = Map.of(
                "nombre", booking.getStudent().getUser().getUserFirstName(),
                "apellido", booking.getStudent().getUser().getUserLastName(),
                "articulo", booking.getItem().getItemName(),
                "cantidad", booking.getItem().getItemQuantity(),
                "hora_inicio", booking.getStartTime(),
                "hora_fin", booking.getEndTime()
        );
        context.setVariables(templateVariables);

        System.out.println(context);
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process("templates/bookingInvoice.html", context);
    }

    private byte[] getBytesFromPdf(String  html){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, outputStream);
        return outputStream.toByteArray();

    }

    public FileInfoResponseDTO getInvoiceFile(Booking  booking){
        String bookingInvoiceTemplate = bookingInvoiceTemplate(booking);
        byte[] pdf = getBytesFromPdf(bookingInvoiceTemplate);
        return new FileInfoResponseDTO(pdf,"fileName");
    }

}
