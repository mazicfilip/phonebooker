package com.mazicfilip.phonebooker.controller;

import com.mazicfilip.phonebooker.dto.ErrorResponseDto;
import com.mazicfilip.phonebooker.dto.PhoneDto;
import com.mazicfilip.phonebooker.service.IPhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST APIs for Phones", description = "REST APIs to FETCH phone details")
@RestController
@RequestMapping(
    path = "/api/phone",
    produces = {MediaType.APPLICATION_JSON_VALUE})
public class PhoneController {

  private final IPhoneService phoneService;

  public PhoneController(IPhoneService phoneService) {
    this.phoneService = phoneService;
  }

  @Operation(summary = "Fetch Phone REST API", description = "REST API to fetch Phone details")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Interval Server Error",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/fetch")
  public ResponseEntity<List<PhoneDto>> fetchPhonesDetails() {
    List<PhoneDto> phonesDto = phoneService.getPhones();

    return ResponseEntity.status(HttpStatus.OK).body(phonesDto);
  }
}
