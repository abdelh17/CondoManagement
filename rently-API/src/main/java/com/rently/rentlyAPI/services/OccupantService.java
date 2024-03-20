package com.rently.rentlyAPI.services;

import com.rently.rentlyAPI.dto.CommonFacilityReservationDto;
import com.rently.rentlyAPI.entity.user.Occupant;

import java.util.List;

public interface OccupantService {
    CommonFacilityReservationDto createCommonFacilityReservation(String token, CommonFacilityReservationDto commonFacilityReservationDto);

    Occupant findOccupantEntityById(Integer occupantId);

    Occupant findOccupantEntityByEmail(String email);

    void deleteCommonFacilityReservation(String token, Integer id);

    CommonFacilityReservationDto getCommonFacilityReservation(String token, Integer id);

    List<CommonFacilityReservationDto> getAllCommonFacilityReservations(String token);
}