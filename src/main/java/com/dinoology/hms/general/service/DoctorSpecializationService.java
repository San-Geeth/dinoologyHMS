package com.dinoology.hms.general.service;

import com.dinoology.hms.general.model.DoctorSpecialization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
public interface DoctorSpecializationService {
    ResponseEntity<?> addNewDoctorSpecialization(HttpServletRequest request, HttpServletResponse response,
                                                 DoctorSpecialization doctorSpecialization);
}
