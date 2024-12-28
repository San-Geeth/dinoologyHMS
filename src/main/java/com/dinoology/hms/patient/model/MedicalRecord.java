package com.dinoology.hms.patient.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/27/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medical_record")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String diseaseName;
    private String medication;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
