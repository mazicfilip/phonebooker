package com.mazicfilip.phonebooker.service;

import com.mazicfilip.phonebooker.dto.PhoneInfoDto;
import com.mazicfilip.phonebooker.exception.FonoApiNotAvailableException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FonoApiClient {

  private final RestTemplate restTemplate;

  @Value("${fonoapi.url}")
  private String fonoapiUrl;

  @Value("${fonoapi.token}")
  private String fonoapiToken;

  public FonoApiClient(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public PhoneInfoDto getPhoneInfo(String phoneName) throws FonoApiNotAvailableException {
    String url = fonoapiUrl + "?token=" + fonoapiToken + "&device=" + phoneName;
    try {
      ResponseEntity<PhoneInfoDto[]> response =
          restTemplate.getForEntity(url, PhoneInfoDto[].class);
      PhoneInfoDto[] phoneInfoArray = response.getBody();
      if (phoneInfoArray != null && phoneInfoArray.length > 0) {
        return phoneInfoArray[0];
      } else {
        throw new FonoApiNotAvailableException(
            String.format("Phone info doesn't exist for %s device", phoneName));
      }
    } catch (RestClientException e) {
      throw new FonoApiNotAvailableException(e.getMessage());
    }
  }
}
