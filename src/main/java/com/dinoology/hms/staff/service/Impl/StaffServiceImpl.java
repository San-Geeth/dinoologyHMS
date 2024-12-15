package com.dinoology.hms.staff.service.Impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.support.SupportMethods;
import com.dinoology.hms.staff.constants.StaffResponseMessageConstants;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.repository.StaffRepository;
import com.dinoology.hms.staff.service.StaffService;
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
 * Created: 12/8/2024 8:29 PM
 */
@Service
public class StaffServiceImpl implements StaffService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StaffRepository staffRepository;

    @Value("${app.staff.id-prefix}")
    private String idPrefix;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public ResponseEntity<?> addStaffMember(HttpServletRequest request, HttpServletResponse response,
                                            StaffMember staffMember) {
        logger.info("Request URI: {}", request.getRequestURI());

        try {
            // Check if staff member already exists by NIC
            if (staffRepository.existsByNic(staffMember.getNic())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(StaffResponseMessageConstants.STAFF_MEMBER_EXISTS));
            }
            // Save the new staff member to the database
            StaffMember newStaffMember = staffRepository.save(staffMember);

            // Change empID and save again
            newStaffMember.setEmpId(generateEMPID(newStaffMember.getId()));
            staffRepository.save(staffMember);

            // Return success response
            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffResponseMessageConstants.STAFF_MEMBER_ADDED_SUCCESSFULLY));

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

    @Override
    public ResponseEntity<?> updateStaffMember(HttpServletRequest request, HttpServletResponse response, StaffMember staffMember) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Optional<StaffMember> existingStaffMemberOptional = staffRepository.findById(staffMember.getId());

            if (existingStaffMemberOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseFail(StaffResponseMessageConstants.STAFF_MEMBER_NOT_FOUND));
            }

            StaffMember existingStaffMember = existingStaffMemberOptional.get();

            if (staffMember.getFirstName() != null && !staffMember.getFirstName().equals(existingStaffMember
                    .getFirstName())) {
                existingStaffMember.setFirstName(staffMember.getFirstName());
            }
            if (staffMember.getLastName() != null && !staffMember.getLastName().equals(existingStaffMember
                    .getLastName())) {
                existingStaffMember.setLastName(staffMember.getLastName());
            }
            if (staffMember.getProfileName() != null && !staffMember.getProfileName().equals(existingStaffMember
                    .getProfileName())) {
                existingStaffMember.setProfileName(staffMember.getProfileName());
            }
            if (staffMember.getNic() != null && !staffMember.getNic().equals(existingStaffMember.getNic())) {
                existingStaffMember.setNic(staffMember.getNic());
            }
            if (staffMember.getEmail() != null && !staffMember.getEmail().equals(existingStaffMember.getEmail())) {
                existingStaffMember.setEmail(staffMember.getEmail());
            }
            if (staffMember.getPrimaryContact() != null && !staffMember.getPrimaryContact().equals(existingStaffMember
                    .getPrimaryContact())) {
                existingStaffMember.setPrimaryContact(staffMember.getPrimaryContact());
            }
            if (staffMember.getSecondaryContact() != null && !staffMember.getSecondaryContact()
                    .equals(existingStaffMember.getSecondaryContact())) {
                existingStaffMember.setSecondaryContact(staffMember.getSecondaryContact());
            }
            if (staffMember.getAddress() != null && !staffMember.getAddress().equals(existingStaffMember
                    .getAddress())) {
                existingStaffMember.setAddress(staffMember.getAddress());
            }
            if (staffMember.getGender() != null && !staffMember.getGender().equals(existingStaffMember.getGender())) {
                existingStaffMember.setGender(staffMember.getGender());
            }
            if (staffMember.getMaritalStatus() != null && !staffMember.getMaritalStatus().equals(existingStaffMember
                    .getMaritalStatus())) {
                existingStaffMember.setMaritalStatus(staffMember.getMaritalStatus());
            }
            if (staffMember.getDesignation() != null && !staffMember.getDesignation().equals(existingStaffMember
                    .getDesignation())) {
                existingStaffMember.setDesignation(staffMember.getDesignation());
            }
            if (staffMember.getSalary() != null && !staffMember.getSalary().equals(existingStaffMember.getSalary())) {
                existingStaffMember.setSalary(staffMember.getSalary());
            }
            if (staffMember.getEmergencyContact() != null && existingStaffMember.getEmergencyContact() != null) {
                existingStaffMember.setEmergencyContact(staffMember.getEmergencyContact());
            }
            if (staffMember.getEmergencyName() != null && existingStaffMember.getEmergencyName() != null) {
                existingStaffMember.setEmergencyName(staffMember.getEmergencyName());
            }
            if (staffMember.getImgURL() != null && !staffMember.getImgURL().equals(existingStaffMember.getImgURL())) {
                existingStaffMember.setImgURL(staffMember.getImgURL());
            }
            if(staffMember.getEmploymentStatus() != null && !staffMember.getEmploymentStatus()
                    .equals(existingStaffMember.getEmploymentStatus())) {
                existingStaffMember.setEmploymentStatus(staffMember.getEmploymentStatus());
            }

            staffRepository.save(existingStaffMember);
            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffResponseMessageConstants.STAFF_MEMBER_UPDATED_SUCCESSFULLY));
        } catch (DataAccessException e) {
            logger.error("Database error while updating staff member: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    /**
     * Generates an employee ID based on the provided ID and prefix
     * @param id the unique staff member ID
     * @return the formatted employee ID
     */
    private String generateEMPID(Integer id) {
        return idPrefix + SupportMethods.formatNumber(id);
    }
}
