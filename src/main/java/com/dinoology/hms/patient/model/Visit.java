package com.dinoology.hms.patient.model;

import com.dinoology.hms.service.model.GeneralService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private LocalDateTime visitDate;
    private String reasonForVisit;
    /*TODO: Break Doctor from staff and treat separately.
    *  Make attendingDoctor data type to Doctor
    */
    private String attendingDoctor;
    private String visitNotes;
}
