package com.dinoology.hms.staff.controller;

import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 8:27 PM
 */
@RestController
@RequestMapping("/staff")

public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/member/add")
    ResponseEntity<?> addStaffMember(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody StaffMember member) {
        return staffService.addStaffMember(request, response, member);
    }

    @PostMapping("member/update")
    ResponseEntity<?> updateStaffMember(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody StaffMember member) {
        return staffService.updateStaffMember(request, response, member);
    }
}
