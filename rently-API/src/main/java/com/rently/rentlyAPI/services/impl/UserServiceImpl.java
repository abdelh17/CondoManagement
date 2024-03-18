package com.rently.rentlyAPI.services.impl;

import com.rently.rentlyAPI.dto.CompanyAdminDto;
import com.rently.rentlyAPI.dto.SystemAdminDto;
import com.rently.rentlyAPI.dto.UserDto;
import com.rently.rentlyAPI.entity.user.User;
import com.rently.rentlyAPI.services.CompanyAdminService;
import com.rently.rentlyAPI.services.SystemAdminService;
import com.rently.rentlyAPI.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final SystemAdminService systemAdminService;
    private final CompanyAdminService companyAdminService;
    
    @Override
    public SystemAdminDto registerSystemAdmin(SystemAdminDto systemAdminDto) {
        return systemAdminService.registerSystemAdmin(systemAdminDto);
    }

    @Override
    public CompanyAdminDto registerCompanyAdmin(CompanyAdminDto companyAdminDto) {
        return companyAdminService.registerCompanyAdminAndLinkToCompany(companyAdminDto);
    }
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final KeyService keyService;
//
//    public PublicUser createUser(RegisterRequestDto request) {
//        String encodedPassword = null;
//
//        if (request.getPassword() != null) {
//            // Encode the password if provided (null with google for example)
//            encodedPassword = passwordEncoder.encode(request.getPassword());
//        }
//        var user = PublicUser.builder()
//                .firstName(request.getFirstname())
//                .lastname(request.getLastname())
//            .email(request.getEmail())
//            .password(encodedPassword)
//            .role(request.getRole())
//            .provider(request.getProvider())
//            .phoneNumber(request.getPhoneNumber())
//            .bio(request.getBio())
//            .build();
//
//	    return userRepository.save(user);
//    }
//
//    public void changePassword(ChangePasswordRequestDto request, Principal connectedUser) {
//
//        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//
//        // check if the current password is correct
//        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            throw new AuthenticationException("The current password is incorrect");
//        }
//        // check if the two new passwords are the same
//        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
//            throw new AuthenticationException("The two passwords do not match");
//        }
//
//        // update the password
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//
//        // save the new password
//        userRepository.save(user);
//    }
//
//    public User updateProfile(UpdateProfileRequestDto request, Integer user_id) {
//        User user = userRepository.findById(user_id)
//            .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        if (request.getFirstname() != null) user.setFirstName(request.getFirstname());
//        if (request.getLastname() != null) user.setLastname(request.getLastname());
//        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
//        if (request.getBio() != null) user.setBio(request.getBio());
//
//        return userRepository.save(user);
//    }
//
//    public UserProfileDto viewProfile(Integer userId) {
//        User user = userRepository.findById(userId)
//            .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        String profilePictureUrl = user.getProfilePicture() != null ? user.getProfilePicture().getStoredUrl() : " ";
//
//        return UserProfileDto.builder()
//            .firstname(user.getFirstName())
//            .lastname(user.getLastname())
//            .email(user.getEmail())
//            .role(user.getRole().name())
//            .phoneNumber(user.getPhoneNumber())
//            .profilePicture(profilePictureUrl)
//            .bio(user.getBio())
//            .build();
//    }
//
//
//    //user becomes RENTER by providing key (user cannot go from owner -> renter)
//    public User activateKeyToChangeRole(String condoKey) {
//
//        //find the key
//        Key key = keyService.findByKey(condoKey);
//
//        //if key is not found
//        if (key == null) {
//            throw new EntityNotFoundException("Key with key " + condoKey + " not found");
//        }
//
//        //find user by key
//         User user = userRepository.findUserByKey(condoKey);
//
//            //if user is not found
//            if (user == null) {
//                throw new EntityNotFoundException("User with key " + condoKey + " not found");
//            }
//
//            //if user is not a renter
//            if (user.getRole() != Role.USER) {
//                throw new OperationNonPermittedException("User with key " + condoKey + " is not allowed to become a RENTER");
//            }
//
//            //if key role is not renter or owner
//            if (key.getRole() != Role.RENTER && key.getRole() != Role.OWNER) {
//                throw new OperationNonPermittedException("Your key ROLE is " + key.getRole() + " and it is not an allowed role.");
//            }
//
//            user.setRole(key.getRole());
//            key.setActive(true);
//            key.setUser(user);
//            keyService.save(key);
//            return userRepository.save(user);
//    }
//
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
    }
//}
