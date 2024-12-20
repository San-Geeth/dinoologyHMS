package com.dinoology.hms.user.service;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.model.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:53 PM
 */
public interface UserService {
    ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, User user);
    ResponseEntity<?> deactivateActivateUser(HttpServletRequest request, HttpServletResponse response, Integer userId,
                                             boolean status);
    ResponseEntity<?> addUserType(HttpServletRequest request, HttpServletResponse response, UserType userType);
    ResponseEntity<?> getAllUsers(HttpServletRequest request, HttpServletResponse response, GeneralPaginationDataRequest paginationRequest);
}
