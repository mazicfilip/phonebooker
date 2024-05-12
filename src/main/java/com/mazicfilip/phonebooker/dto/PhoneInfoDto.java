package com.mazicfilip.phonebooker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneInfoDto {

    @Schema(description = "Technology", example = "GSM / CDMA / HSPA / EVDO / LTE / 5G")
    private String technology;

    @Schema(description = "2G frequency bands for communication", example = "GSM 850 / 900 / 1800 / 1900")
    private String _2gBands;

    @Schema(description = "3G frequency bands for communication", example = "HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100")
    private String _3gBands;

    @Schema(description = "4G frequency bands for communication", example = "LTE band 1(2100), 2(1900), 3(1800)")
    private String _4gBands;
}
