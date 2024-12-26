package com.dinoology.hms.service.controller;

import com.dinoology.hms.service.model.GeneralService;
import com.dinoology.hms.service.service.GeneralServiceService;
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
@RequestMapping(("/service"))
public class GeneralServiceController {

    private final GeneralServiceService generalServiceService;

    public GeneralServiceController(GeneralServiceService generalServiceService) {
        this.generalServiceService = generalServiceService;
    }

    @PostMapping("/add")
    ResponseEntity<?> addnewGeneralService(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody GeneralService generalService) {
        return generalServiceService.addNewService(request, response, generalService);
    }
}
