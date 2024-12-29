package com.dinoology.hms.staff.dto.request;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Data
public class DoctorDTO {
    private String firstName;
    private String lastName;
    private String profileName;
    private String education;
    private String appointedHospital;
    private String nic;
    private String email;
    private String primaryContact;
    private String secondaryContact;
    private String address;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer rate;
    private String emergencyContact;
    private String emergencyName;
    private String imgURL;

    //Transients
    private Integer specialization_id;
}
