package com.dinoology.hms.user.service.impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.repository.StaffRepository;
import com.dinoology.hms.user.constants.UserConstants;
import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.repository.UserRepository;
import com.dinoology.hms.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.user.initial-password}")
    private String initialPassword;

    public UserServiceImpl(UserRepository userRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            // TODO: If 'isExternal' is false:
            // 1. Fetch the associated Staff member details using the provided user information.
            // 2. Check if the Staff member has an email address:
            //    - If yes, set the email as the username.
            //    - If no, generate a username using the Staff member's first name.
            // 3. Generate a temporary password for the user.
            // 4. Send an email with the generated username and temporary password (if an email address exists).
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
