package com.mazicfilip.phonebooker.mapper;

import com.mazicfilip.phonebooker.dto.ReservationDto;
import com.mazicfilip.phonebooker.entity.ReservationEntity;

public class ReservationMapper {
  public static ReservationDto mapToReservationDto(ReservationEntity reservationEntity) {
    return ReservationDto.builder()
        .id(reservationEntity.getId())
        .reservedBy(reservationEntity.getReservedBy())
        .createdAt(reservationEntity.getCreatedAt())
        .released(reservationEntity.getReleased())
        .phone(reservationEntity.getPhone().getName())
        .build();
  }
}
