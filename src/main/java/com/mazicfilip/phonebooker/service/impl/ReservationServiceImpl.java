package com.mazicfilip.phonebooker.service.impl;

import com.mazicfilip.phonebooker.dto.ReservationDto;
import com.mazicfilip.phonebooker.entity.PhoneEntity;
import com.mazicfilip.phonebooker.entity.ReservationEntity;
import com.mazicfilip.phonebooker.exception.PhoneNotAvailableException;
import com.mazicfilip.phonebooker.exception.ReservationReleasedException;
import com.mazicfilip.phonebooker.exception.ResourceNotFoundException;
import com.mazicfilip.phonebooker.mapper.ReservationMapper;
import com.mazicfilip.phonebooker.repository.PhoneRepository;
import com.mazicfilip.phonebooker.repository.ReservationRepository;
import com.mazicfilip.phonebooker.request.ReservationRequest;
import com.mazicfilip.phonebooker.service.IReservationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {
  private ReservationRepository reservationRepository;
  private PhoneRepository phoneRepository;

  @Override
  public List<ReservationDto> getReservations() {
    List<ReservationEntity> reservations = reservationRepository.findAll();

    return reservations.stream()
        .map(ReservationMapper::mapToReservationDto)
        .collect(Collectors.toList());
  }

  @Override
  public ReservationDto createReservation(ReservationRequest reservationRequest) {

    PhoneEntity phone = getPhoneById(reservationRequest.getPhoneId());
    if (phone.isAvailable()) {
      reduceNumberOfDevicesAvailable(phone);
      ReservationEntity reservationEntity = createNewReservation(reservationRequest, phone);
      return ReservationMapper.mapToReservationDto(reservationEntity);
    } else {
      throw new PhoneNotAvailableException(reservationRequest.getPhoneId());
    }
  }

  @Override
  public ReservationDto releaseReservation(Long reservationId) {
    ReservationEntity reservationEntity = getReservationById(reservationId);

    if (reservationEntity.getReleased() == null) {
      reservationEntity.setReleased(LocalDateTime.now());
      ReservationEntity reservation = reservationRepository.save(reservationEntity);
      if (reservationEntity.getPhone() != null)
        increaseNumberOfDevicesAvailable(reservationEntity.getPhone().getId());
      return ReservationMapper.mapToReservationDto(reservation);
    } else {
      throw new ReservationReleasedException(reservationId);
    }
  }

  private PhoneEntity getPhoneById(Long phoneId) {
    return phoneRepository
        .findById(phoneId)
        .orElseThrow(() -> new ResourceNotFoundException("Phone", "Id", String.valueOf(phoneId)));
  }

  private void reduceNumberOfDevicesAvailable(PhoneEntity phone) {
    int availableQuantity = phone.getAvailableQuantity() - 1;
    phone.setAvailableQuantity(availableQuantity);
    if (availableQuantity == 0) {
      phone.setAvailable(false);
    }
    phoneRepository.save(phone);
  }

  private void increaseNumberOfDevicesAvailable(Long phoneId) {
    PhoneEntity phone = getPhoneById(phoneId);
    phone.setAvailable(true);
    phone.setAvailableQuantity(phone.getAvailableQuantity() + 1);
    phoneRepository.save(phone);
  }

  private ReservationEntity createNewReservation(
      ReservationRequest reservationRequest, PhoneEntity phone) {
    ReservationEntity reservationEntity = new ReservationEntity();
    reservationEntity.setReservedBy(reservationRequest.getReservedBy());
    reservationEntity.setPhone(phone);
    reservationEntity.setCreatedAt(LocalDateTime.now());
    return reservationRepository.save(reservationEntity);
  }

  private ReservationEntity getReservationById(Long reservationId) {
    return reservationRepository
        .findById(reservationId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("Reservation", "Id", String.valueOf(reservationId)));
  }
}
