package com.dinoology.hms.patient.controller;

import com.dinoology.hms.patient.model.Patient;
import com.dinoology.hms.patient.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by: sangeethnawa
 * Date:2024 12/27/2024
 * Copyright © 2024 DinooLogy
 */
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    ResponseEntity<?> addNewPatient(HttpServletRequest request, HttpServletResponse response, @RequestBody Patient patient) {
        return patientService.addNewPatient(request,response, patient);
    }
}
