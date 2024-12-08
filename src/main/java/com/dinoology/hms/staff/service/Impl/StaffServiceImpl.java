package com.dinoology.hms.staff.service.Impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.support.SupportMethods;
import com.dinoology.hms.staff.constants.StaffConstants;
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
                        .body(new ResponseWrapper<>().responseFail(StaffConstants.STAFF_MEMBER_EXISTS));
            }
            // Save the new staff member to the database
            StaffMember newStaffMember = staffRepository.save(staffMember);

            // Change empID and save again
            newStaffMember.setEmpId(generateEMPID(newStaffMember.getId()));
            staffRepository.save(staffMember);

            // Return success response
            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffConstants.STAFF_MEMBER_ADDED_SUCCESSFULLY));

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

    /**
     * Generates an employee ID based on the provided ID and prefix
     * @param id the unique staff member ID
     * @return the formatted employee ID
     */
    private String generateEMPID(Integer id) {
        return idPrefix + SupportMethods.formatNumber(id);
    }
}
