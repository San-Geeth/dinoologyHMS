package com.dinoology.hms.staff.model;

import com.dinoology.hms.common_utility.enums.Gender;
import com.dinoology.hms.common_utility.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 8:28 PM
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff_member")
public class StaffMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String profileName;
    private String nic;
    private String email;
    private String primaryContact;
    private String secondaryContact;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private String designation;
    private String salary;
    private String empId;
    private String emergencyContact;
    private String emergencyName;
    private Boolean employementStatus = true;
    private String imgURL;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "StaffMember{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileName='" + profileName + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", primaryContact='" + primaryContact + '\'' +
                ", secondaryContact='" + secondaryContact + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", empId='" + empId + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyName='" + emergencyName + '\'' +
                ", employementStatus=" + employementStatus +
                ", imgURL='" + imgURL + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
