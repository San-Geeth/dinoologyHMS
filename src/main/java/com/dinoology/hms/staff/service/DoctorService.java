package com.dinoology.hms.staff.service;

import com.dinoology.hms.staff.model.Doctor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/*
 * Created by: sangeethnawa
 * Date:2024 12/28/2024
 * Copyright © 2024 DinooLogy
 */
public interface DoctorService {
    ResponseEntity<?> addNewDoctor(HttpServletRequest request, HttpServletResponse response, Doctor doctor);
}
