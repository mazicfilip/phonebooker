package com.mazicfilip.phonebooker.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@Table(name = "phone")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToOne(mappedBy = "phone", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PhoneInfoEntity info;

    private int totalQuantity;
    private int availableQuantity;

    @ColumnDefault(value = "true")
    private boolean isAvailable;

    @OneToMany(mappedBy = "phone")
    private List<ReservationEntity> reservations;
}
