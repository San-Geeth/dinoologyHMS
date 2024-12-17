package com.dinoology.hms.user.controller;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.model.UserType;
import com.dinoology.hms.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* Author: sangeethnawa
* Created: 12/8/2024 6:24 PM
*/
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        return userService.addUser(request, response, user);
    }

    @PostMapping("/deactivate/{userId}")
    ResponseEntity<?> deactivateUser(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable Integer userId, @RequestParam boolean status) {
        return userService.deactivateActivateUser(request, response, userId, status);
    }

    @PostMapping("/add/user-type")
    ResponseEntity<?> addUserType(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody UserType userType) {
        return userService.addUserType(request, response, userType);
    }

    @PostMapping("/get-all")
    ResponseEntity<?> getAllUser(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody GeneralPaginationDataRequest paginationRequest) {
        return userService.getAllUsers(request, response, paginationRequest);
    }
}
