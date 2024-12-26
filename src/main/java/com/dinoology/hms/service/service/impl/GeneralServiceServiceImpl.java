package com.dinoology.hms.service.service.impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.service.constants.GeneralServiceResponseMessageConstants;
import com.dinoology.hms.service.repository.GeneralServiceRepository;
import com.dinoology.hms.service.service.GeneralServiceService;
import com.dinoology.hms.service.model.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            generalService.setService(normalizedKey);

            if (generalServiceRepository.existsByServiceKey(generalService.getServiceKey())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>()
                                .responseFail(GeneralServiceResponseMessageConstants.SERVICE_ALREADY_EXISTS));
            }

            GeneralService newService = generalServiceRepository.save(generalService);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(GeneralServiceResponseMessageConstants.SERVICE_ADDED_SUCCESSFULLY, newService));

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
}
