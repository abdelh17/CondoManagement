package com.rently.rentlyAPI.repository;


import com.rently.rentlyAPI.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
	
	List<Building> findAllByCompanyId(Integer companyId);

}