package com.dinoology.hms.staff.service;

import com.dinoology.hms.staff.model.StaffMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 8:28 PM
 */
public interface StaffService {
    ResponseEntity<?> addStaffMember(HttpServletRequest request, HttpServletResponse response, StaffMember staffMember);
}
