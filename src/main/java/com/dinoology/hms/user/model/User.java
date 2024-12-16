package com.dinoology.hms.user.model;

import com.dinoology.hms.staff.model.StaffMember;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:48 PM
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String userEmail;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean isExternal = false;
    private Boolean isActive = true;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_member_id", referencedColumnName = "id")
    private StaffMember staffMember;
    @ManyToOne
    @JoinColumn(name = "user_type", referencedColumnName = "id")
    private UserType userType;

    @Transient
    private Integer staffMemberId;
    @Transient
    private Integer userTypeId;

    public User(Integer id, String username, String userEmail, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
        this.username = username;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", isExternal=" + isExternal +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", staffMember=" + staffMember +
                ", staffMemberId=" + staffMemberId +
                '}';
    }
}
