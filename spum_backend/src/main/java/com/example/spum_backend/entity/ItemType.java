package com.example.spum_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item_types")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemTypeId;

    private String itemTypeName;

    @OneToMany(mappedBy = "itemType")
    private List<Item> items;

}
