package com.rently.rentlyAPI.services;

import com.rently.rentlyAPI.dto.BuildingDto;
import com.rently.rentlyAPI.dto.CondoDto;
import com.rently.rentlyAPI.dto.KeyDto;
import com.rently.rentlyAPI.entity.Condo;

import java.util.List;

public interface CompanyService {
  BuildingDto createBuildingByCompanyId(Integer companyId, BuildingDto buildingDto);
  BuildingDto getBuildingByCompanyIdAndBuildingId(Integer companyId, Integer buildingId);
  List<BuildingDto> getAllBuildingsByCompanyId(Integer companyId);
  Integer countCondosById(Integer buildingId);
  
  List<CondoDto> findAllCondosByBuildingId(Integer buildingId);
  CondoDto createCondoByCompanyId(Integer companyId, Integer buildingId, CondoDto condoDto);
  KeyDto createActivationKeyToBecomeRenter(String userEmail, Integer companyId);
  KeyDto createActivationKeyToBecomeOwner(String userEmail, Integer companyId);
}
