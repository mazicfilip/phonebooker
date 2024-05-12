package com.mazicfilip.phonebooker.mapper;

import com.mazicfilip.phonebooker.dto.PhoneDto;
import com.mazicfilip.phonebooker.dto.PhoneInfoDto;
import com.mazicfilip.phonebooker.entity.PhoneEntity;
import com.mazicfilip.phonebooker.entity.PhoneInfoEntity;

public class PhoneMapper {
  public static PhoneDto mapToPhoneDto(PhoneEntity phoneEntity) {
    PhoneInfoEntity infoEntity = phoneEntity.getInfo();
    PhoneInfoDto phoneInfo =
        (infoEntity != null)
            ? PhoneInfoDto.builder()
                .technology(infoEntity.getTechnology())
                ._2gBands(infoEntity.get_2gBands())
                ._3gBands(infoEntity.get_3gBands())
                ._4gBands(infoEntity.get_4gBands())
                .build()
            : null;

    return PhoneDto.builder()
        .id(phoneEntity.getId())
        .name(phoneEntity.getName())
        .info(phoneInfo)
        .totalQuantity(phoneEntity.getTotalQuantity())
        .availableQuantity(phoneEntity.getAvailableQuantity())
        .isAvailable(phoneEntity.isAvailable())
        .build();
  }

  public static PhoneDto mapToPhoneDto(PhoneEntity phoneEntity, PhoneInfoDto phoneInfo) {
    return PhoneDto.builder()
        .id(phoneEntity.getId())
        .name(phoneEntity.getName())
        .info(phoneInfo)
        .totalQuantity(phoneEntity.getTotalQuantity())
        .availableQuantity(phoneEntity.getAvailableQuantity())
        .isAvailable(phoneEntity.isAvailable())
        .build();
  }
}
