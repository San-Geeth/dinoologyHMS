package com.dinoology.hms.patient.service.impl;

import com.dinoology.hms.common_utility.enums.Platform;
import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.support.SupportMethods;
import com.dinoology.hms.patient.constants.PatientResponseMessageConstants;
import com.dinoology.hms.patient.dto.request.PatientDTO;
import com.dinoology.hms.patient.model.Patient;
import com.dinoology.hms.patient.model.Visit;
import com.dinoology.hms.patient.repository.PatientRepository;
import com.dinoology.hms.patient.repository.VisitRepository;
import com.dinoology.hms.patient.service.PatientService;
import com.dinoology.hms.general.model.GeneralService;
import com.dinoology.hms.general.repository.GeneralServiceRepository;
import com.dinoology.hms.staff.model.Doctor;
import com.dinoology.hms.staff.repository.DoctorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final GeneralServiceRepository generalServiceRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Value("${app.patient.id-prefix}")
    private String pidPrefix;

    public PatientServiceImpl(PatientRepository patientRepository, VisitRepository visitRepository, GeneralServiceRepository generalServiceRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
        this.generalServiceRepository = generalServiceRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> addNewPatient(HttpServletRequest request, HttpServletResponse response, PatientDTO patientDTO) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Patient patient = modelMapper.map(patientDTO, Patient.class);
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

            if(patient.getPlatform().equals(Platform.PREMISES) && patientDTO.getVisit() != null) {
                /*TODO: Learn this mapping
                * Matching Strategy: ModelMapper uses a default matching strategy that looks for similar property names
                * between the source and destination objects. Since serviceId in VisitDTO and id in
                * Visit both contain "id," ModelMapper maps them incorrectly.
                * No Explicit Mapping: Without explicit mappings to guide ModelMapper, it assumes
                * the properties are related because of their partial name match.
                * If such cases, use below way
                * modelMapper.typeMap(VisitDTO.class, Visit.class).addMappings(mapper -> {
                    mapper.skip(Visit::setId);
                    });
                */
                Visit visit = modelMapper.map(patientDTO.getVisit(), Visit.class);
                GeneralService generalService = generalServiceRepository
                        .findByGeneralServiceId(
                                patientDTO
                                        .getVisit()
                                        .getService_id()
                        );
                Doctor doctor = doctorRepository.findDoctorByPK(patientDTO.getVisit().getDoc_id());
                if(doctor != null) {
                    visit.setAttendingDoctor(doctor);
                }
                if(visit != null && generalService != null) {
                    initiateFirstVisit(savedPatient, visit, generalService);
                }
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

    private void initiateFirstVisit(Patient patient, Visit visit, GeneralService generalService) {
        logger.info("Started first visit initiation.....");
        try {
            visit.setPatient(patient);
            visit.setService(generalService);
            visitRepository.save(visit);
            logger.info("First visit initiation completed!");
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            throw e;
        }
    }

}