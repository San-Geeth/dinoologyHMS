package com.dinoology.hms.staff.model;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.MaritalStatus;
import com.dinoology.hms.general.model.DoctorSpecialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/28/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String profileName;
    private String education;
    private String appointedHospital;
    private String nic;
    private String email;
    private Long primaryContact;
    private Long secondaryContact;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private Integer rate;
    private String did;
    private String emergencyContact;
    private String emergencyName;
    private Boolean activeStatus = true;
    private String imgURL;
    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private DoctorSpecialization doctorSpecialization;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
