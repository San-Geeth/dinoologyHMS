package com.dinoology.hms.patient.dto.request;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.Platform;
import com.dinoology.hms.common_utility.enums.Title;
import com.dinoology.hms.patient.model.Visit;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Data
public class PatientDTO {
    private String pid;
    private String firstName;
    private String lastName;
    private String nic;
    private Long contact;
    private String email;
    private String address;
    private Gender gender;
    private Title title;
    private Platform platform;
    private LocalDate dob;
    private Integer age;
    private VisitDTO visit;
}
