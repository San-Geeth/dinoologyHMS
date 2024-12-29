package com.dinoology.hms.general.controller;

import com.dinoology.hms.common_utility.dto.request.GeneralPaginationDataRequest;
import com.dinoology.hms.general.model.DoctorSpecialization;
import com.dinoology.hms.general.model.GeneralService;
import com.dinoology.hms.general.service.DoctorSpecializationService;
import com.dinoology.hms.general.service.GeneralServiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by: sangeethnawa
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
@RestController
@RequestMapping(("/general"))
public class GeneralController {

    private final GeneralServiceService generalServiceService;
    private final DoctorSpecializationService doctorSpecializationService;

    public GeneralController(GeneralServiceService generalServiceService, DoctorSpecializationService doctorSpecializationService) {
        this.generalServiceService = generalServiceService;
        this.doctorSpecializationService = doctorSpecializationService;
    }

    @PostMapping("/service/add")
    ResponseEntity<?> addnewGeneralService(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody GeneralService generalService) {
        return generalServiceService.addNewService(request, response, generalService);
    }

    @PostMapping("/service/update")
    ResponseEntity<?> updateGeneralService(HttpServletRequest request, HttpServletResponse response,
                                           @RequestBody GeneralService generalService) {
        return generalServiceService.editGeneralService(request, response, generalService);
    }

    @PostMapping("/service/get-all")
    ResponseEntity<?> getAllGeneralServices(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody GeneralPaginationDataRequest generalPaginationDataRequest) {
        return generalServiceService.getAllGeneralServices(request, response, generalPaginationDataRequest);
    }

    @PostMapping("/specialization/add")
    ResponseEntity<?> addNewDoctorSpecialization(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestBody DoctorSpecialization doctorSpecialization) {
        return doctorSpecializationService.addNewDoctorSpecialization(request, response, doctorSpecialization);
    }
}
