package com.dinoology.hms.patient.model;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.Title;
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
    private String pid;
    private String firstName;
    private String lastName;
    private String nic;
    private String contact;
    private String gender;
    @Enumerated(EnumType.STRING)
    private Title title;
    private LocalDate dob;
    private Integer age;
    private Boolean isActive = true;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
