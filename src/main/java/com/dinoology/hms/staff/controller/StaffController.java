package com.dinoology.hms.staff.controller;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.staff.model.Designation;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.service.DesignationService;
import com.dinoology.hms.staff.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final DesignationService designationService;

    public StaffController(StaffService staffService, DesignationService designationService) {
        this.staffService = staffService;
        this.designationService = designationService;
    }

    @PostMapping("/member/add")
    ResponseEntity<?> addStaffMember(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody StaffMember member) {
        return staffService.addStaffMember(request, response, member);
    }

    @PostMapping("/member/update")
    ResponseEntity<?> updateStaffMember(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody StaffMember member) {
        return staffService.updateStaffMember(request, response, member);
    }

    @PostMapping("/member/get-all")
    ResponseEntity<?> getAllStaffMembers(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody GeneralPaginationDataRequest paginationRequest) {
        return staffService.getAllStaffMembers(request, response, paginationRequest);
    }

    @PostMapping("/designation/add")
    ResponseEntity<?> addNewDesignation(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody Designation designation) {
        return designationService.addNewDesignation(request, response, designation);
    }

    @PostMapping("/designation/update")
    ResponseEntity<?> editDesignation(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody Designation designation) {
        return designationService.editDesignation(request, response, designation);
    }

    @PostMapping("/designation/get-all")
    ResponseEntity<?> getAlLDesignations(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody GeneralPaginationDataRequest paginationRequest) {
        return designationService.getAllDesignations(request, response, paginationRequest);
    }
}
