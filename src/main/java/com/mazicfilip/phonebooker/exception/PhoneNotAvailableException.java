package com.mazicfilip.phonebooker.exception;

public class PhoneNotAvailableException extends RuntimeException {

    public PhoneNotAvailableException(Long id){
        super(String.format("Phone with id: %s is not available", id));
    }
}
