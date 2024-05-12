package com.mazicfilip.phonebooker.service;

import com.mazicfilip.phonebooker.dto.ReservationDto;
import com.mazicfilip.phonebooker.request.ReservationRequest;

import java.util.List;

public interface IReservationService {
    List<ReservationDto> getReservations();
    ReservationDto createReservation(ReservationRequest reservationRequest);
    ReservationDto releaseReservation(Long reservationId);
}
