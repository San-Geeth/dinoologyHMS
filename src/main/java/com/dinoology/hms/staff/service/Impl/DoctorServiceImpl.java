package com.dinoology.hms.staff.service.Impl;

import com.dinoology.hms.common_utility.response.ResponseWrapper;
import com.dinoology.hms.common_utility.support.SupportMethods;
import com.dinoology.hms.general.model.DoctorSpecialization;
import com.dinoology.hms.general.repository.DoctorSpecializationRepository;
import com.dinoology.hms.staff.constants.StaffResponseMessageConstants;
import com.dinoology.hms.staff.dto.request.DoctorDTO;
import com.dinoology.hms.staff.model.Doctor;
import com.dinoology.hms.staff.repository.DoctorRepository;
import com.dinoology.hms.staff.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
 * Created by: sangeethnawa
 * Date:2024 12/28/2024
 * Copyright © 2024 DinooLogy
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorSpecializationRepository doctorSpecializationRepository;
    @Value("${app.doctor.id-prefix}")
    private String doctorIdPrefix;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DoctorRepository doctorRepository;
    private final DoctorSpecializationRepository specializationRepository;
    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorSpecializationRepository specializationRepository, ModelMapper modelMapper, DoctorSpecializationRepository doctorSpecializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
        this.modelMapper = modelMapper;
        this.doctorSpecializationRepository = doctorSpecializationRepository;
    }


    @Override
    public ResponseEntity<?> addNewDoctor(HttpServletRequest request, HttpServletResponse response, DoctorDTO doctorDTO) {
        logger.info("Request URI: {}", request.getRequestURI());
        try {
            Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
            doctor.setPrimaryContact(SupportMethods.formatContact(doctor.getPrimaryContact()));
            doctor.setSecondaryContact(SupportMethods.formatContact(doctor.getSecondaryContact()));

            if (doctorRepository.existsByNic(doctor.getNic())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseWrapper<>().responseFail(StaffResponseMessageConstants.DOCTOR_ALREADY_EXISTS));
            }

            if(doctorDTO.getSpecialization_id() != null) {
                DoctorSpecialization doctorSpecialization = doctorSpecializationRepository
                        .getDoctorSpecializationById(doctorDTO.getSpecialization_id());
                if(doctorSpecialization != null) {
                    doctor.setDoctorSpecialization(doctorSpecialization);
                }
            }

            Doctor newDoctor = doctorRepository.save(doctor);
            newDoctor.setDid(generateDOCID(newDoctor.getId()));
            Doctor savedDoctor = doctorRepository.save(newDoctor);

            return ResponseEntity.ok().body(new ResponseWrapper<>()
                    .responseOk(StaffResponseMessageConstants.DOCTOR_ADDED_SUCCESSFULLY, savedDoctor));
        } catch (DataAccessException e) {
            logger.error("Database error while adding doctor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("Database error occurred"));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail("An unexpected error occurred"));
        }
    }

    private String generateDOCID(Integer id) {
        return doctorIdPrefix + SupportMethods.formatNumber(id);
    }
}
