package com.mazicfilip.phonebooker.controller;

import com.mazicfilip.phonebooker.dto.ErrorResponseDto;
import com.mazicfilip.phonebooker.dto.ReservationDto;
import com.mazicfilip.phonebooker.request.ReservationRequest;
import com.mazicfilip.phonebooker.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REST APIs for Reservations", description = "REST APIs to FETCH, CREATE and RELEASE phone reservations")
@RestController
@RequestMapping(
    path = "/api/reservation",
    produces = {MediaType.APPLICATION_JSON_VALUE})
public class ReservationController {

  private final IReservationService reservationService;

  public ReservationController(IReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Operation(
      summary = "Fetch phone reservations REST API",
      description = "REST API to fetch phone reservations details")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Interval Server Error",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/fetch")
  public ResponseEntity<List<ReservationDto>> fetchReservationsDetails() {
    List<ReservationDto> reservationsDto = reservationService.getReservations();

    return ResponseEntity.status(HttpStatus.OK).body(reservationsDto);
  }

  @Operation(
      summary = "Create phone reservation REST API",
      description = "REST API to create phone reservation")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Interval Server Error",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PostMapping("/create")
  public ResponseEntity<ReservationDto> createReservation(
      @Valid @RequestBody ReservationRequest reservationRequest) {
    ReservationDto reservationDto = reservationService.createReservation(reservationRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(reservationDto);
  }

  @Operation(
      summary = "Release phone reservation REST API",
      description = "REST API to release phone reservation")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Interval Server Error",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PostMapping("/release")
  public ResponseEntity<ReservationDto> releaseReservation(
      @Valid @RequestParam Long reservationId) {
    ReservationDto reservationDto = reservationService.releaseReservation(reservationId);

    return ResponseEntity.status(HttpStatus.OK).body(reservationDto);
  }
}
