package com.dinoology.hms.patient.service.impl;

import com.dinoology.hms.common_utility.enums.Platform;
import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.support.SupportMethods;
import com.dinoology.hms.patient.constants.PatientResponseMessageConstants;
import com.dinoology.hms.patient.model.Patient;
import com.dinoology.hms.patient.model.Visit;
import com.dinoology.hms.patient.repository.PatientRepository;
import com.dinoology.hms.patient.repository.VisitRepository;
import com.dinoology.hms.patient.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

/*
 * Created by: sangeethnawa
 * Date:2024 12/27/2024
 * Copyright © 2024 DinooLogy
 */
@Service
public class PatientServiceImpl implements PatientService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    @Value("${app.patient.id-prefix}")
    private String pidPrefix;

    public PatientServiceImpl(PatientRepository patientRepository, VisitRepository visitRepository) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    public ResponseEntity<?> addNewPatient(HttpServletRequest request, HttpServletResponse response, Patient patient) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            // Check if staff member already exists by NIC
            patient.setContact(SupportMethods.formatContact(patient.getContact()));
            if (patientRepository.existsByContact(patient.getContact())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(PatientResponseMessageConstants
                                .PATIENT_ALREADY_REGISTERED));
            }
            if (patient.getDob() != null) {
                LocalDate dob = patient.getDob();
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(dob, currentDate).getYears();
                patient.setAge(age);
            } else if (patient.getAge() != null) {
                patient.setAge(patient.getAge());
            }

            Patient newPatient = patientRepository.save(patient);

            newPatient.setPid(generatePATID(newPatient.getId()));
            Patient savedPatient = patientRepository.save(newPatient);

            //TODO: Initiate first visit
            if(patient.getPlatform().equals(Platform.PREMISES) && patient.getVisit() != null) {
                initiateFirstVisit(savedPatient, patient.getVisit());
            }
            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(PatientResponseMessageConstants.PATIENT_ADDED_SUCCESSFULLY, savedPatient));

        } catch (DataAccessException e) {
            logger.error("Database error while adding patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    private String generatePATID(Integer id) {
        return pidPrefix + SupportMethods.formatTo10Digit(id);
    }

    private void initiateFirstVisit(Patient patient, Visit visit) {
        logger.info("Started first visit initiation.....");
        try {
            visit.setPatient(patient);
            visitRepository.save(visit);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            throw e;
        }
    }

}
