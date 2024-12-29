package com.dinoology.hms.general.service.impl;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.general.constants.GeneralServiceResponseMessageConstants;
import com.dinoology.hms.general.repository.GeneralServiceRepository;
import com.dinoology.hms.general.service.GeneralServiceService;
import com.dinoology.hms.general.model.GeneralService;
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
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
@Service
public class GeneralServiceServiceImpl implements GeneralServiceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final GeneralServiceRepository generalServiceRepository;

    public GeneralServiceServiceImpl(GeneralServiceRepository generalServiceRepository) {
        this.generalServiceRepository = generalServiceRepository;
    }

    @Override
    public ResponseEntity<?> addNewService(HttpServletRequest request, HttpServletResponse response, GeneralService generalService) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            String normalizedKey = generalService.getServiceKey().toLowerCase().replace(" ", "_");
            generalService.setServiceKey(normalizedKey);

            if (generalServiceRepository.existsByServiceKey(generalService.getServiceKey())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>()
                                .responseFail(GeneralServiceResponseMessageConstants.SERVICE_ALREADY_EXISTS));
            }

            GeneralService newService = generalServiceRepository.save(generalService);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(GeneralServiceResponseMessageConstants.SERVICE_ADDED_SUCCESSFULLY, newService));

        } catch (DataAccessException e) {
            logger.error("Database error while adding general service: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> editGeneralService(HttpServletRequest request, HttpServletResponse response, GeneralService generalService) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            String normalizedKey = generalService.getServiceKey().toLowerCase().replace(" ", "_");
            generalService.setServiceKey(normalizedKey);

            Optional<GeneralService> existingGeneralServiceOptional = generalServiceRepository.findById(generalService.getId());

            if (existingGeneralServiceOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseFail(GeneralServiceResponseMessageConstants.SERVICE_NOT_FOUND));
            }

            GeneralService existingGeneralService = existingGeneralServiceOptional.get();



            if (generalService.getServiceKey() != null && !generalService.getServiceKey().equals(existingGeneralService
                    .getServiceKey())) {
                if (generalServiceRepository.existsByServiceKey(generalService.getServiceKey())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ResponseWrapper<>()
                                    .responseFail(GeneralServiceResponseMessageConstants.SERVICE_ALREADY_EXISTS));
                } else  {
                    existingGeneralService.setServiceKey(generalService.getServiceKey());
                }
            }
            if(generalService.getService() != null && !generalService.getService()
                    .equals(existingGeneralService.getService())) {
                existingGeneralService.setService(generalService.getService());
            }

            GeneralService updatedGeneralService = generalServiceRepository.save(existingGeneralService);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(GeneralServiceResponseMessageConstants.SERVICE_UPDATED_SUCCESSFULLY, updatedGeneralService));

        } catch (DataAccessException e) {
            logger.error("Database error while updating general service: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    @Override
    public ResponseEntity<?> getAllGeneralServices(HttpServletRequest request, HttpServletResponse response,
                                                   GeneralPaginationDataRequest paginationRequest) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
            Page<GeneralService> generalServicePage = generalServiceRepository.findAll(pageable);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("services", generalServicePage.getContent());
            responseBody.put("currentPage", generalServicePage.getNumber());
            responseBody.put("totalItems", generalServicePage.getTotalElements());
            responseBody.put("totalPages", generalServicePage.getTotalPages());

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(responseBody));
        } catch (DataAccessException e) {
            logger.error("Database error while getting all general services: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }
}
