package com.dinoology.hms.patient.service;

import com.dinoology.hms.patient.dto.request.PatientDTO;
import com.dinoology.hms.patient.model.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/*
 * Created by: sangeethnawa
 * Date:2024 12/27/2024
 * Copyright © 2024 DinooLogy
 */
public interface PatientService {
    ResponseEntity<?> addNewPatient(HttpServletRequest request, HttpServletResponse response, PatientDTO patient);
}
