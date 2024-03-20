package com.rently.rentlyAPI.repository;

import com.rently.rentlyAPI.entity.CommonFacilityReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonFacilityReservationRepository extends JpaRepository<CommonFacilityReservation, Integer> {
    Optional<CommonFacilityReservation> findByOccupantIdAndDate(Integer occupantId, String reservationDate);
}
