package com.dinoology.hms.staff.service.Impl;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.staff.constants.StaffResponseMessageConstants;
import com.dinoology.hms.staff.model.Designation;
import com.dinoology.hms.staff.model.StaffMember;
import com.dinoology.hms.staff.repository.DesignationRepository;
import com.dinoology.hms.staff.service.DesignationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * Created by: sangeethnawa
 * Date:2024 12/16/2024
 * Copyright © 2024 DinooLogy
 */
@Service
public class DesignationServiceImpl implements DesignationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DesignationRepository designationRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @Override
    public ResponseEntity<?> addNewDesignation(HttpServletRequest request, HttpServletResponse response,
                                               Designation designation) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            String normalizedKey = designation.getDesignationKey().toLowerCase().replace(" ", "_");;
            designation.setDesignationKey(normalizedKey);

            if (designationRepository.existsByDesignationKey(designation.getDesignationKey())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>()
                                .responseFail(StaffResponseMessageConstants.DESIGNATION_ALREADY_EXISTS));
            }

            Designation newDesignation = designationRepository.save(designation);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffResponseMessageConstants.DESIGNATION_ADDED_SUCCESSFULLY, newDesignation));

        } catch (DataAccessException e) {
            logger.error("Database error while adding designation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> editDesignation(HttpServletRequest request, HttpServletResponse response, Designation designation) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            String normalizedKey = designation.getDesignationKey().toLowerCase().replace(" ", "_");;
            designation.setDesignationKey(normalizedKey);

            if (designationRepository.existsByDesignationKey(designation.getDesignationKey())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>()
                                .responseFail(StaffResponseMessageConstants.DESIGNATION_ALREADY_EXISTS));
            }

            Optional<Designation> existingDesignationOptional = designationRepository.findById(designation.getId());

            if (existingDesignationOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseFail(StaffResponseMessageConstants.DESIGNATION_NOT_FOUND));
            }

            Designation existingDesignation = existingDesignationOptional.get();

            if (designation.getDesignationKey() != null && !designation.getDesignationKey().equals(existingDesignation
                    .getDesignationKey())) {
                existingDesignation.setDesignationKey(designation.getDesignationKey());
            }
            if(designation.getDesignation() != null && !designation.getDesignation()
                    .equals(existingDesignation.getDesignation())) {
                existingDesignation.setDesignation(designation.getDesignation());
            }

            Designation updatedDesignation = designationRepository.save(existingDesignation);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffResponseMessageConstants.DESIGNATION_UPDATED_SUCCESSFULLY, updatedDesignation));

        } catch (DataAccessException e) {
            logger.error("Database error while updating designation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> getAllDesignations(HttpServletRequest request, HttpServletResponse response,
                                                GeneralPaginationDataRequest paginationRequest) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
            Page<Designation> designationPage = designationRepository.findAll(pageable);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("staffMembers", designationPage.getContent());
            responseBody.put("currentPage", designationPage.getNumber());
            responseBody.put("totalItems", designationPage.getTotalElements());
            responseBody.put("totalPages", designationPage.getTotalPages());

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(responseBody));
        } catch (DataAccessException e) {
            logger.error("Database error while getting all designation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }
}
