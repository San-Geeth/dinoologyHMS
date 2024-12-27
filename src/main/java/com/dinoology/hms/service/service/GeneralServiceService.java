package com.dinoology.hms.service.service;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.service.model.GeneralService;
import com.dinoology.hms.staff.model.Designation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/*
 * Created by: sangeethnawa
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
public interface GeneralServiceService {
    ResponseEntity<?> addNewService(HttpServletRequest request, HttpServletResponse response,
                                    GeneralService generalService);
    ResponseEntity<?> editGeneralService(HttpServletRequest request, HttpServletResponse response,
                                      GeneralService generalService);
    ResponseEntity<?> getAllGeneralServices(HttpServletRequest request, HttpServletResponse response,
                                         GeneralPaginationDataRequest paginationRequest);
}
