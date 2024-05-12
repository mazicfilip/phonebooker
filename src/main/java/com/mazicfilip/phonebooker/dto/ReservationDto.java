package com.mazicfilip.phonebooker.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "ReservationResponse", description = "Schema to hold reservation response information")
public class ReservationDto {

  @Schema(description = "Reservation id", example = "2")
  private Long id;

  @Schema(description = "Name of the person who reserved the phone", example = "Tom Higins")
  private String reservedBy;

  @Schema(description = "Date and time when reservation created", example = "2024-05-12T11:25:40.610343")
  private LocalDateTime createdAt;

  @Schema(description = "Date and time when reservation released", example = "2024-05-12T11:25:40.610343")
  private LocalDateTime released;

  @Schema(description = "Device name", example = "IPhone 15 Pro")
  private String phone;

}
