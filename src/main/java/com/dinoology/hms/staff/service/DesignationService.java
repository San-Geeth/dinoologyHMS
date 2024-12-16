package com.dinoology.hms.staff.service;

import com.dinoology.hms.staff.model.Designation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/*
 * Created by: sangeethnawa
 * Date:2024 12/16/2024
 * Copyright © 2024 DinooLogy
 */
public interface DesignationService {
    ResponseEntity<?> addNewDesignation(HttpServletRequest request, HttpServletResponse response,
                                        Designation designation);
    ResponseEntity<?> editDesignation(HttpServletRequest request, HttpServletResponse response,
                                      Designation designation);
}
