package com.mazicfilip.phonebooker.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mazicfilip.phonebooker.dto.ReservationDto;
import com.mazicfilip.phonebooker.entity.PhoneEntity;
import com.mazicfilip.phonebooker.entity.ReservationEntity;
import com.mazicfilip.phonebooker.exception.PhoneNotAvailableException;
import com.mazicfilip.phonebooker.exception.ReservationReleasedException;
import com.mazicfilip.phonebooker.repository.PhoneRepository;
import com.mazicfilip.phonebooker.repository.ReservationRepository;
import com.mazicfilip.phonebooker.request.ReservationRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
  @Mock private ReservationRepository reservationRepository;

  @Mock private PhoneRepository phoneRepository;

  @InjectMocks private ReservationServiceImpl reservationService;

  @Test
  public void testGetReservations() {
    ReservationEntity reservationEntity = new ReservationEntity();
    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setId(1L);
    phoneEntity.setName("Samsung Galaxy S10");
    reservationEntity.setId(1L);
    reservationEntity.setPhone(phoneEntity);

    when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservationEntity));

    Iterable<ReservationDto> reservations = reservationService.getReservations();

    assertEquals(1, reservations.spliterator().getExactSizeIfKnown());
    assertEquals(1L, reservations.iterator().next().getId());
  }

  @Test
  public void testCreateReservation_Success() {
    ReservationRequest reservationRequest =
        ReservationRequest.builder().phoneId(1L).reservedBy("Filip").build();

    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setAvailable(true);

    ReservationEntity savedReservationEntity = new ReservationEntity();
    savedReservationEntity.setId(1L);
    savedReservationEntity.setReservedBy("Filip");
    savedReservationEntity.setPhone(phoneEntity);
    savedReservationEntity.setCreatedAt(LocalDateTime.now());

    when(phoneRepository.findById(1L)).thenReturn(Optional.of(phoneEntity));
    when(reservationRepository.save(any(ReservationEntity.class)))
        .thenReturn(savedReservationEntity);

    ReservationDto reservationDto = reservationService.createReservation(reservationRequest);

    assertNotNull(reservationDto);
    assertEquals("Filip", reservationDto.getReservedBy());
    assertTrue(phoneEntity.isAvailable());
  }

  @Test
  public void testCreateReservation_PhoneNotAvailable() {
    ReservationRequest reservationRequest = ReservationRequest.builder().phoneId(1L).build();

    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setAvailable(false);

    when(phoneRepository.findById(1L)).thenReturn(Optional.of(phoneEntity));

    assertThrows(
        PhoneNotAvailableException.class,
        () -> reservationService.createReservation(reservationRequest));
  }

  @Test
  public void testReleaseReservation_Success() {

    ReservationEntity reservationEntity = new ReservationEntity();
    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setName("Samsung Galaxy S10");
    phoneEntity.setId(1L);

    reservationEntity.setId(1L);
    reservationEntity.setPhone(phoneEntity);
    reservationEntity.setReleased(null);

    when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));
    when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
    when(phoneRepository.findById(1L)).thenReturn(Optional.of(phoneEntity));

    ReservationDto reservationDto = reservationService.releaseReservation(1L);

    assertNotNull(reservationDto);
    assertEquals(1L, reservationDto.getId());
    assertNotNull(reservationEntity.getReleased());
    assertTrue(phoneEntity.isAvailable());
  }

  @Test
  public void testReleaseReservation_AlreadyReleased() {

    ReservationEntity reservationEntity = new ReservationEntity();
    reservationEntity.setId(1L);
    reservationEntity.setReleased(LocalDateTime.now());

    when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));

    assertThrows(
        ReservationReleasedException.class, () -> reservationService.releaseReservation(1L));
  }
}
