package com.dinoology.hms.patient.model;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.Title;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/25/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String nic;
    private String contact;
    private String email;
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Title title;

    private LocalDate dob;
    private Integer age;
    private String medicalRecordNumber;
    private LocalDateTime registrationDate;
    private LocalDateTime lastVisitDate;
    private Boolean isActive;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
