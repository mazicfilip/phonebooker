package com.mazicfilip.phonebooker.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mazicfilip.phonebooker.dto.PhoneDto;
import com.mazicfilip.phonebooker.dto.PhoneInfoDto;
import com.mazicfilip.phonebooker.entity.PhoneEntity;
import com.mazicfilip.phonebooker.exception.FonoApiNotAvailableException;
import com.mazicfilip.phonebooker.repository.PhoneRepository;
import com.mazicfilip.phonebooker.service.FonoApiClient;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {
  @Mock private PhoneRepository phoneRepository;

  @Mock private FonoApiClient fonoApiClient;

  @InjectMocks private PhoneServiceImpl phoneService;

  @Test
  public void testGetPhones_Success() throws FonoApiNotAvailableException {

    PhoneEntity phoneEntity1 = new PhoneEntity();
    phoneEntity1.setId(1L);
    phoneEntity1.setName("Phone1");

    PhoneEntity phoneEntity2 = new PhoneEntity();
    phoneEntity2.setId(2L);
    phoneEntity2.setName("Phone2");

    List<PhoneEntity> phoneEntities = Arrays.asList(phoneEntity1, phoneEntity2);

    when(phoneRepository.findAll()).thenReturn(phoneEntities);

    when(fonoApiClient.getPhoneInfo("Phone1"))
        .thenReturn(PhoneInfoDto.builder().technology("Tech Example").build());
    when(fonoApiClient.getPhoneInfo("Phone2")).thenThrow(FonoApiNotAvailableException.class);

    List<PhoneDto> phones = phoneService.getPhones();

    assertEquals(2, phones.size());
    assertEquals(1L, phones.get(0).getId());
    assertEquals("Phone1", phones.get(0).getName());
    assertEquals("Tech Example", phones.get(0).getInfo().getTechnology());
    assertEquals(2L, phones.get(1).getId());
    assertEquals("Phone2", phones.get(1).getName());

    verify(phoneRepository, times(1)).findAll();
    verify(fonoApiClient, times(1)).getPhoneInfo("Phone1");
    verify(fonoApiClient, times(1)).getPhoneInfo("Phone2");
  }
}
