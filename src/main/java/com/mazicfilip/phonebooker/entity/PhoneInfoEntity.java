package com.mazicfilip.phonebooker.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "phone_info")
public class PhoneInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;

    @OneToOne
    @MapsId
    @JoinColumn(name = "phone_id")
    private PhoneEntity phone;
}
