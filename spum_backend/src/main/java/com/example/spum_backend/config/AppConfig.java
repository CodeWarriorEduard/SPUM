package com.example.spum_backend.config;

import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.entity.Booking;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Value("${java.mail.username}")
    private String username;
    @Value("${java.mail.password}")
    private String password;
    @Value("${java.mail.port}")
    private int port;
    @Value("${java.mail.host}")
    private String host;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.addMappings(new PropertyMap<Booking, BookingResponseDTO>() {

            @Override
            protected void configure() {
                map().setStartTime(source.getStartTime());
                map().setEndTime(source.getEndTime());
                map().getStudent().setStudentName(source.getStudent().getUser().getUserFirstName());
                map().getStudent().setEmail(source.getStudent().getUser().getEmail());
            }

        });
        return modelMapper  ;
    }


    @Bean
    public JavaMailSender mailSender() {
        System.out.println(username + " " + password + " " + host + " " + port);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }




}
