package com.dinoology.hms.user.service.impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.services.MailService;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.repository.StaffRepository;
import com.dinoology.hms.user.constants.UserConstants;
import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.repository.UserRepository;
import com.dinoology.hms.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:53 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final MailService mailService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.user.initial-password}")
    private String initialPassword;

    public UserServiceImpl(UserRepository userRepository, StaffRepository staffRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            User newUser = new User();
            if(!user.getIsExternal()) {
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
                    */
                    if(staffMember.getEmail() != null) {
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
                            .body(new ResponseWrapper<>().responseFail(UserConstants.STAFF_MEMBER_FOR_USER_NOT_FOUND));
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

    // TODO: Enhance this with best practices
    @Override
    public ResponseEntity<?> deactivateUser(HttpServletRequest request, HttpServletResponse response, Integer userId) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            User checkingUser = userRepository.findByUserId(userId);
            if(checkingUser != null) {
                int rowAffected = userRepository.deactivateUser(userId);
                if (rowAffected == 1) {
                    logger.info("Account Deactivated Successfully: {}", userId);

                    Optional<User> verifyingUpdate = userRepository.findById(userId);
                    return verifyingUpdate.map(user ->
                            ResponseEntity.ok().body(new ResponseWrapper<>().responseOk(user))
                    ).orElseGet(() -> {
                        logger.error("Failed to fetch updated user after deactivation: {}", userId);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseWrapper<>().responseFail("User not found after deactivation"));
                    });
                } else {
                    logger.info("Account Deactivation Failed: {}", userId);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
                }
            } else {
                return ResponseEntity.ok().body(new ResponseWrapper<>().responseOk(UserConstants.ACCOUNT_NOT_FOUND));
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

    private ResponseEntity<?> getUserResponse(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            logger.info("Avoiding adding user: {}, user already exists!", user.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseWrapper<>().responseFail(UserConstants.USERNAME_FOUND));
        } else {
            userRepository.save(user);
            logger.info("User Added Successfully: {}", user);
            return ResponseEntity.ok().body(new ResponseWrapper<>().responseOk(UserConstants.USER_ADDED_SUCCESSFULLY));
        }
    }
}
