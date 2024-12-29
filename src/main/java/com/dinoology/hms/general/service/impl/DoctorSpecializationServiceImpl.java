package com.dinoology.hms.general.service.impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.general.constants.GeneralServiceResponseMessageConstants;
import com.dinoology.hms.general.model.DoctorSpecialization;
import com.dinoology.hms.general.repository.DoctorSpecializationRepository;
import com.dinoology.hms.general.service.DoctorSpecializationService;
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
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Service
public class DoctorSpecializationServiceImpl implements DoctorSpecializationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DoctorSpecializationRepository doctorSpecializationRepository;

    public DoctorSpecializationServiceImpl(DoctorSpecializationRepository doctorSpecializationRepository) {
        this.doctorSpecializationRepository = doctorSpecializationRepository;
    }

    @Override
    public ResponseEntity<?> addNewDoctorSpecialization(HttpServletRequest request, HttpServletResponse response,
                                                        DoctorSpecialization doctorSpecialization) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            String normalizedKey = doctorSpecialization.getSpecializationKey().toLowerCase()
                    .replace(" ", "_");
            doctorSpecialization.setSpecializationKey(normalizedKey);

            if (doctorSpecializationRepository.existsBySpecializationKey(doctorSpecialization.getSpecializationKey())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>()
                                .responseFail(GeneralServiceResponseMessageConstants.SPECIALIZATION_ALREADY_EXISTS));
            }

            DoctorSpecialization newDoctorSpecialization = doctorSpecializationRepository.save(doctorSpecialization);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(GeneralServiceResponseMessageConstants.SPECIALIZATION_ADDED_SUCCESSFULLY, newDoctorSpecialization));

        } catch (DataAccessException e) {
            logger.error("Database error while adding doctor specialization: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }
}
