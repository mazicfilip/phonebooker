package com.mazicfilip.phonebooker.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "ReservationRequest", description = "Schema to hold reservation request information")
public class ReservationRequest {

  @NotEmpty(message = "ReservedBy can not be a null or empty")
  @Schema(description = "Name of the person who reserved the phone", example = "Tom Higins")
  private String reservedBy;

  @NotEmpty(message = "PhoneId can not be a null or empty")
  @Schema(description = "Id of the phone to be reserved", example = "2")
  private Long phoneId;
}
