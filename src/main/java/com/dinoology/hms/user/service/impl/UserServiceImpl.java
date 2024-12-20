package com.dinoology.hms.user.service.impl;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.services.MailService;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.repository.StaffRepository;
import com.dinoology.hms.user.constants.UserResponseMessageConstants;
import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.model.UserType;
import com.dinoology.hms.user.repository.UserRepository;
import com.dinoology.hms.user.repository.UserTypeRepository;
import com.dinoology.hms.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:53 PM
 */
@Service
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final UserTypeRepository userTypeRepository;
    private final MailService mailService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.user.initial-password}")
    private String initialPassword;
    @Value("${app.user.reset-mail-active}")
    private boolean resetMailActive;

    public UserServiceImpl(UserRepository userRepository, StaffRepository staffRepository, MailService mailService, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.mailService = mailService;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            UserType userType = userTypeRepository.findById(user.getUserTypeId())
                    .orElse(null); // Get the value or null if not present

            if(userType == null) {
                logger.warn("User type not found for ID: {}", user.getUserTypeId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.USER_TYPE_NOT_FOUND));
            }

            user.setUserType(userType);

            User newUser = new User();
            if(!user.getIsExternal()) {
                newUser.setUserType(userType);
                Optional<StaffMember> staffMemberOptional = staffRepository.findById(user.getStaffMemberId());
                if (staffMemberOptional.isPresent()) {
                    StaffMember staffMember = staffMemberOptional.get();
                    newUser.setUsername(staffMember.getEmpId());
                    if(staffMember.getEmail() != null) {
                        newUser.setUserEmail(staffMember.getEmail());
                    }
                    newUser.setPassword(initialPassword);
                    newUser.setStaffMember(staffMember);

                    /*
                    TODO: Enhance below to send proper content and proper data
                        - need to build redirection URL to frontend with token
                        - need to get token to verify password change when user changing password correctly
                        - if change success user must redirect to login screen of frontend
                        - send email asynchronously
                    */
                    if(staffMember.getEmail() != null && resetMailActive) {
                        try {
                            String mailContent = mailService.buildPasswordResetEmail("https://www.lipsum.com/");
                            mailService.sendHtmlEmail(staffMember.getEmail(), "Reset your password", mailContent);
                        } catch (MessagingException e) {
                            logger.error("Error while sending email: {}", e.getMessage());
                        }
                    }
                    return getUserResponse(newUser);
                } else {
                    logger.info("Avoiding adding user for id: {}, staff member not found!", user.getStaffMemberId());
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.STAFF_MEMBER_FOR_USER_NOT_FOUND));
                }
            } else {
                return getUserResponse(user);
            }
        } catch (DataAccessException e) {
            logger.error("Database error while adding staff member: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> deactivateActivateUser(HttpServletRequest request, HttpServletResponse response,
                                                    Integer userId, boolean status) {
        logger.info("Received deactivate user request. URI: {}, UserId: {}", request.getRequestURI(), userId);
        try {
            // Fetch the user to check if it exists
            User checkingUser = userRepository.findByUserId(userId);
            if (checkingUser == null) {
                logger.warn("User not found for ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.ACCOUNT_NOT_FOUND));
            }

            if (!checkingUser.getIsActive() && !status) {
                logger.warn("Account already deactivated for ID: {}", userId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.ACCOUNT_IN_DEACTIVATED));
            }

            if (checkingUser.getIsActive() && status) {
                logger.warn("Account already in active status for ID: {}", userId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.ACCOUNT_IN_ACTIVATED));
            }

            // Attempt to deactivate the user
            int rowsAffected = userRepository.deactivateActivateUser(userId, status);
            if (rowsAffected != 1) {
                logger.error("Failed to deactivate user. UserId: {}", userId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.UNABLE_TO_APPLY_CHANGES));
            }

            logger.info("User successfully deactivated. UserId: {}", userId);
            if (status) {
                return ResponseEntity.ok(
                        new ResponseWrapper<>()
                                .responseOk("User Account has been successfully activated.")
                );
            } else {
                return ResponseEntity.ok(
                        new ResponseWrapper<>()
                                .responseOk("User has been successfully deactivated.")
                );
            }
        } catch (DataAccessException ex) {
            logger.error("Database error while deactivating user. UserId: {}, Error: {}", userId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception ex) {
            logger.error("Unexpected error while deactivating user. UserId: {}, Error: {}", userId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> addUserType(HttpServletRequest request, HttpServletResponse response, UserType userType) {
        logger.info("Received user add user request. URI: {}, data: {}", request.getRequestURI(), userType);
        try {
            if (userType.getType() != null) {
                userType.setType(userType.getType().toUpperCase());
            }

            // Check if the type already exists
            if (userTypeRepository.existsByType(userType.getType())) {
                logger.warn("Type already defined: {}", userType.getType());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.USER_TYPE_EXISTS));
            }

            // Validate type length
            if (!(userType.getType().length() > 1 && userType.getType().length() <= 50)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.USER_TYPE_MUST_BETWEEN));
            }

            // Save the new user type
            UserType newUserType = userTypeRepository.save(userType);
            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(UserResponseMessageConstants.USER_TYPE_ADDED_SUCCESSFULLY, newUserType));
        } catch (DataAccessException e) {
            logger.error("Database error while adding user type: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers(HttpServletRequest request, HttpServletResponse response, GeneralPaginationDataRequest paginationRequest) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
            Page<User> userPage = userRepository.findAll(pageable);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("users", userPage.getContent());
            responseBody.put("currentPage", userPage.getNumber());
            responseBody.put("totalItems", userPage.getTotalElements());
            responseBody.put("totalPages", userPage.getTotalPages());

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(responseBody));
        } catch (DataAccessException e) {
            logger.error("Database error while getting all users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    private ResponseEntity<?> getUserResponse(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            logger.info("Avoiding adding user: {}, user already exists!", user.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseWrapper<>().responseFail(UserResponseMessageConstants.USERNAME_FOUND));
        } else {
            userRepository.save(user);
            logger.info("User Added Successfully: {}", user);
            return ResponseEntity.ok().body(new ResponseWrapper<>().responseOk(UserResponseMessageConstants.USER_ADDED_SUCCESSFULLY));
        }
    }
}
