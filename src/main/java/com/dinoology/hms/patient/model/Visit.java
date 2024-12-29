package com.dinoology.hms.patient.model;

import com.dinoology.hms.service.model.GeneralService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Created by: sangeethnawa
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
@Table(name = "visit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    private String reasonForVisit;
    /*TODO: Break Doctor from staff and treat separately.
     *  Make attendingDoctor data type to Doctor
     */
    private String attendingDoctor;
    private String visitNotes;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private GeneralService service;
}