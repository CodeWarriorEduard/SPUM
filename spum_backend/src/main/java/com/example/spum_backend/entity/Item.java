package com.example.spum_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;
    private String itemDescription;
    private Long itemQuantity;

    @ManyToOne
    @JoinColumn(name = "itemTypeId", referencedColumnName = "itemTypeId")
    private ItemType itemType;

    @OneToMany
    @JsonIgnore
    private List<Booking> bookings;

    @Override
    public String toString() {
        return "Item{" +
                "itemType=" + itemType.getItemTypeName() +
                ", itemQuantity=" + itemQuantity +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemId=" + itemId +
                '}';
    }
}
