package com.dinoology.hms.user.service.impl;

import com.dinoology.hms.user.model.User;
import com.dinoology.hms.user.repository.UserRepository;
import com.dinoology.hms.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:53 PM
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    @Override
    public ResponseEntity<?> addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        logger.info(request.getRequestURI());
        try {
            // TODO: If 'isExternal' is false:
            // 1. Fetch the associated Staff member details using the provided user information.
            // 2. Check if the Staff member has an email address:
            //    - If yes, set the email as the username.
            //    - If no, generate a username using the Staff member's first name.
            // 3. Generate a temporary password for the user.
            // 4. Send an email with the generated username and temporary password (if an email address exists).
            if(userRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Available");
            } else {
                User newUser = userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
