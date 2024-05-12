package com.mazicfilip.phonebooker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "reservation")
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String reservedBy;
  
  @Column(updatable = false)
  private LocalDateTime createdAt;

  private LocalDateTime released;

  @ManyToOne
  @JoinColumn(name = "phone_id")
  private PhoneEntity phone;
}
