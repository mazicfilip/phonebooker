package com.mazicfilip.phonebooker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "PhoneResponse", description = "Schema to hold Phone information")
public class PhoneDto {
    @Schema(description = "Phone id", example = "1")
    private Long id;

    @Schema(description = "Device name", example = "IPhone 15 Pro")
    private String name;

    @Schema(description = "Phone info")
    private PhoneInfoDto info;

    @Schema(description = "Number of devices", example = "2")
    private int totalQuantity;

    @Schema(description = "Available number of devices for reservation", example = "1")
    private int availableQuantity;

    @Schema(description = "Whether the device available for reservation", example = "true")
    private boolean isAvailable;
}
