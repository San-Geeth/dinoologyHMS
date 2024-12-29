package com.dinoology.hms.general.service;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.general.model.GeneralService;
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
