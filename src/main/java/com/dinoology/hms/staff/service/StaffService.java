package com.dinoology.hms.staff.service;

import com.dinoology.hms.staff.dto.request.GetAllStaffMembers;
import com.dinoology.hms.staff.model.StaffMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 8:28 PM
 */
public interface StaffService {
    ResponseEntity<?> addStaffMember(HttpServletRequest request, HttpServletResponse response, StaffMember staffMember);
    ResponseEntity<?> updateStaffMember(HttpServletRequest request, HttpServletResponse response, StaffMember staffMember);
    ResponseEntity<?> getAllStaffMembers(HttpServletRequest request, HttpServletResponse response,
                                         GetAllStaffMembers getAllStaffMembersDAO);
}
