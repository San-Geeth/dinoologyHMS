package com.dinoology.hms.user.controller;

import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
                                     @PathVariable Integer userId) {
        return userService.deactivateUser(request, response, userId);
    }
}
