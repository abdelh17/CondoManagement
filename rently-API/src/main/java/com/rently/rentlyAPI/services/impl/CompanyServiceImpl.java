package com.rently.rentlyAPI.services.impl;

import com.rently.rentlyAPI.dto.CompanyDto;
import com.rently.rentlyAPI.entity.Company;
import com.rently.rentlyAPI.exceptions.OperationNonPermittedException;
import com.rently.rentlyAPI.repository.CompanyRepository;
import com.rently.rentlyAPI.services.CompanyService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    
    @Override
    public CompanyDto findCompanyDtoById(Integer companyId) {
        
        Company company = findCompanyEntityById(companyId);
        return CompanyDto.fromEntity(company);
    }
    
    @Override
    public Company findCompanyEntityById(Integer companyId) {
        
        return companyRepository.findById(companyId)
            .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found"));
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        // Check if the company already exists
        Optional<Company> company = companyRepository.findCompanyByName(companyDto.getName());
        
        // If the company is found, throw an exception
        if(company.isPresent()){
            throw new OperationNonPermittedException("There is already a company with name: "+companyDto.getName());
        }
        
        // Convert the CompanyDto to an entity and save it
        Company companyToSave = CompanyDto.toEntity(companyDto);
        Company savedCompany = companyRepository.save(companyToSave);
        
        return CompanyDto.fromEntity(savedCompany);
    }
    
    @Override
    public CompanyDto updateCompany(CompanyDto companyDto) {
        
        // Find the Company Entity by its ID
        Company companyToUpdate = findCompanyEntityById(companyDto.getId());

        // Update Company details if present
        if (companyDto.getName() != null && !companyDto.getName().isEmpty()) {
            companyToUpdate.setName(companyDto.getName());
        }

        // Save the updated Company
        Company updatedCompany = companyRepository.save(companyToUpdate);
        
        return CompanyDto.fromEntity(updatedCompany);
    }

    @Override
    public void deleteCompanyById(Integer companyId) {
        
        // Attempt to retrieve the company by ID
        Company companyToDelete = findCompanyEntityById(companyId);
        
        // If the company is found, delete it
        companyRepository.delete(companyToDelete);
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(CompanyDto::fromEntity)
                .toList();
    }

//    private final BuildingService buildingService;
//    private final CondoService condoService;
//    private final UserRepository userRepository;
//    private final CompanyRepository companyRepository;
//
//    private final ObjectsValidator<Object> validator;
//    private final KeyService keyService;
//    private final CondoRepository condoRepository; // unused
//
//
//    @Override
//    public Company save(Company company) {
//        return companyRepository.save(company);
//
//    }
//
//    // Create a building by company ID
//    @Override
//    public BuildingDto createBuildingByCompanyId(Integer companyId, BuildingDto buildingDto) {
//
//        //Check if the user exists
//        Company company = companyRepository.findById(companyId)
//            .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//        //TODO: Validate the buildingDto
//
////        if(company.getRole() != Role.COMPANY){
////            throw new OperationNonPermittedException("Only a User with role COMPANY can create a building.");
////        }
//
//        Building building = BuildingDto.toEntity(buildingDto);
//        building.setCompany(company);
//        Building savedBuilding = buildingService.save(building);
//        return BuildingDto.fromEntity(savedBuilding);
//    }
//
//    // Get a building by company ID and building ID
//    @Override
//    public BuildingDto getBuildingByCompanyIdAndBuildingId(Integer companyId, Integer buildingId) {
//
//          // Check if the user exists
//          companyRepository.findById(companyId)
//              .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//          // Check if the building exists
//          if(!buildingService.exists(buildingId)){
//              throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//          }
//
//          Building building = buildingService.findById(buildingId);
//          return BuildingDto.fromEntity(building);
//    }
//
//    @Override
//    public BuildingDto updateBuildingByCompanyIdAndBuildingId(Integer companyId, Integer buildingId, BuildingDto buildingDto) {
//        //Check if the user exists
//        Company company = companyRepository.findById(companyId)
//            .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//        //TODO: Validate the buildingDto
//
////        if(company.getRole() != Role.COMPANY){
////            throw new OperationNonPermittedException("Only a User with role COMPANY can create a building.");
////        }
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//        Building building = buildingService.findById(buildingId);
//        Building updatedBuilding = buildingService.update(buildingDto, building);
//
//        return BuildingDto.fromEntity(updatedBuilding);
//    }
//
//    @Override
//    public void deleteBuildingByCompanyIdAndBuildingId(Integer companyId, Integer buildingId) {
//
//        // Check if the user exists
//        companyRepository.findById(companyId)
//            .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//
//        Building building = buildingService.findById(buildingId);
//        // Delete the building
//        buildingService.delete(building);
//
//    }
//
//    // Get all buildings by company ID
//    @Override
//    public List<BuildingDto> getAllBuildingsByCompanyId(Integer companyId) {
//
//        // Check if the user exists
//        companyRepository.findById(companyId)
//            .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//        List<Building> buildings = buildingService.findAllByCompanyId(companyId);
//        return buildings.stream().map(BuildingDto::fromEntity).collect(Collectors.toList());
//    }
//
//    // Count condos by building ID
//    @Override
//    public Integer countCondosById(Integer buildingId) {
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//        return condoService.countCondosByBuildingId(buildingId);
//    }
//
//    // Get all buildings by company ID
//    @Override
//    public List<CondoDto> findAllCondosByBuildingId(Integer buildingId) {
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//
//        List<Condo> condos = condoService.findAllCondosByBuildingId(buildingId);
//        return condos.stream().map(CondoDto::fromEntity).collect(Collectors.toList());
//    }
//
//    // Create a condo by company ID and building ID (associate the condo with the building)
//    @Override
//    public CondoDto createCondoByCompanyId(Integer companyId, Integer buildingId, CondoDto condoDto) {
//        // Check if the user exists
//        Company user = companyRepository.findById(companyId)
//            .orElseThrow(() -> new EntityNotFoundException("User with ID " + companyId + " not found"));
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//
//        // Check if the user has the role COMPANY
////        if(user.getRole() != Role.COMPANY){
////            throw new OperationNonPermittedException("Only a User with role COMPANY can create a condo.");
////        }
//
//        // Validate the condoDto
//        validator.validate(condoDto);
//
//        // Find the building by its ID
//        Building building = buildingService.findById(buildingId);
//
//        // Convert the condoDto to entity and associate it with the building and user
//        Condo condoEntity = CondoDto.toEntity(condoDto);
//        condoEntity.setBuilding(building); // Assuming Condo entity has a setBuilding method to establish the relationship
////        condoEntity.setUser(user); // Set the user in the condo entity if needed
//
//        // Save the condo entity
//        Condo savedCondo = condoService.save(condoEntity);
//
//        return CondoDto.fromEntity(savedCondo);
//    }
//
//    // Update a condo by building ID and condo ID
//
//
//    @Override
//    public CondoDto getCondoByBuildingIdAndCondoId(Integer buildingId, Integer condoId) {
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//
//        // Check if the condo exists
//        if(!condoService.exists(condoId)){
//            throw new EntityNotFoundException("Condo with ID " + condoId + " not found");
//        }
//
//        // Find the condo by its ID
//        Condo condo = condoService.findById(condoId);
//        return CondoDto.fromEntity(condo);
//    }
//
//    @Override
//    public CondoDto updateCondoByBuildingIdAndCondoId(Integer buildingId, Integer condoId, CondoDto condoDto) {
//
//          // Check if the building exists
//          if(!buildingService.exists(buildingId)){
//              throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//          }
//
//          // Check if the condo exists
//          if(!condoService.exists(condoId)){
//              throw new EntityNotFoundException("Condo with ID " + condoId + " not found");
//          }
//
//          // Find the condo by its ID
//          Condo condo = condoService.findById(condoId);
//
//          // Update the condo entity
//          Condo updatedCondo = condoService.update(condoDto, condo);
//
//          return CondoDto.fromEntity(updatedCondo);
//    }
//
//    @Override
//    public void deleteCondoByBuildingIdAndCondoId(Integer buildingId, Integer condoId) {
//
//        // Check if the building exists
//        if(!buildingService.exists(buildingId)){
//            throw new EntityNotFoundException("Building with ID " + buildingId + " not found");
//        }
//
//        // Check if the condo exists
//        if(!condoService.exists(condoId)){
//            throw new EntityNotFoundException("Condo with ID " + condoId + " not found");
//        }
//
//        Condo condo = condoService.findById(condoId);
//        condoService.delete(condo);
//
//    }
//
//
//    public KeyDto createActivationKeyToBecomeRenter(String userEmail, Integer companyId) {
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " not found"));
//
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found"));
//
//        if (user.getRole() != Role.USER) {
//            throw new OperationNonPermittedException("User with email " + userEmail + " is not allowed to become a RENTER");
//        }
//
////        if (company.getRole() != Role.COMPANY) {
////            throw new OperationNonPermittedException("User with email " + userEmail + " is not allowed to attribute a key to a non company user");
////        }
//
//        //Build a keyDto with builder
//        KeyDto keyDto = KeyDto.builder()
//                .userId(user.getId())
//                .key(activationKeyGenerator())
//                .isActive(false)
//                .revoked(false)
//                .companyId(companyId)
//                .role(Role.RENTER)
//                .build();
//
//        // save the key
//
//        Key keyEntity = KeyDto.toEntity(keyDto);
//        keyEntity.setUser(user);
//        return KeyDto.fromEntity(keyService.save(keyEntity));
//
//    }
//
//    public static String activationKeyGenerator() {
//        int keyLength = 16;
//        String characters = "0123456789";
//        StringBuilder activationKey = new StringBuilder();
//
//        SecureRandom secureRandom = new SecureRandom();
//
//        for (int i = 0; i < keyLength; i++) {
//            int randomIndex = secureRandom.nextInt(characters.length());
//            char randomChar = characters.charAt(randomIndex);
//            activationKey.append(randomChar);
//        }
//
//        return activationKey.toString();
//    }
//
//
//    public KeyDto createActivationKeyToBecomeOwner(String userEmail, Integer companyId) {
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new EntityNotFoundException("User with email " + userEmail + " not found"));
//
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + companyId + " not found"));
//
//        if (user.getRole() != Role.USER) {
//            throw new OperationNonPermittedException("User with email " + userEmail + " is not allowed to become a RENTER");
//        }
//
////        if (company.getRole() != Role.COMPANY) {
////            throw new OperationNonPermittedException("User with email " + userEmail + " is not allowed to attribute a key to a non company user");
////        }
//
//        //Build a keyDto with builder
//        KeyDto keyDto = KeyDto.builder()
//                .userId(user.getId())
//                .key(activationKeyGenerator())
//                .isActive(false)
//                .revoked(false)
//                .companyId(companyId)
//                .role(Role.OWNER)
//                .build();
//
//        // save the key
//
//        Key keyEntity = KeyDto.toEntity(keyDto);
//        keyEntity.setUser(user);
//        return KeyDto.fromEntity(keyService.save(keyEntity));
//    }
//

}
