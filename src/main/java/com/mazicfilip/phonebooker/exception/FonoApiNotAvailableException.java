package com.mazicfilip.phonebooker.exception;

public class FonoApiNotAvailableException extends Exception {

  public FonoApiNotAvailableException(String message) {
    super(String.format("FonoApi not available: %s", message));
  }
}
