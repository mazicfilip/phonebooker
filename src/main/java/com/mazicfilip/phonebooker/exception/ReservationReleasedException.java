package com.mazicfilip.phonebooker.exception;

public class ReservationReleasedException extends RuntimeException {

    public ReservationReleasedException(Long id){
        super(String.format("Reservation with id: %s is released", id));
    }
}
