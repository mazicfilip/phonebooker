package com.mazicfilip.phonebooker.service.impl;

import com.mazicfilip.phonebooker.dto.PhoneDto;
import com.mazicfilip.phonebooker.dto.PhoneInfoDto;
import com.mazicfilip.phonebooker.entity.PhoneEntity;
import com.mazicfilip.phonebooker.exception.FonoApiNotAvailableException;
import com.mazicfilip.phonebooker.mapper.PhoneMapper;
import com.mazicfilip.phonebooker.repository.PhoneRepository;
import com.mazicfilip.phonebooker.service.FonoApiClient;
import com.mazicfilip.phonebooker.service.IPhoneService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneServiceImpl implements IPhoneService {

  private PhoneRepository phoneRepository;
  private FonoApiClient fonoApiClient;

  @Override
  public List<PhoneDto> getPhones() {
    List<PhoneEntity> phones = phoneRepository.findAll();
    return phones.stream()
        .map(
            phone -> {
              try {
                PhoneInfoDto phoneInfo = fonoApiClient.getPhoneInfo(phone.getName());
                return PhoneMapper.mapToPhoneDto(phone, phoneInfo);
              } catch (FonoApiNotAvailableException e) {
                return PhoneMapper.mapToPhoneDto(phone);
              }
            })
        .collect(Collectors.toList());
  }
}
