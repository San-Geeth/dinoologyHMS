package com.dinoology.hms.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
 * Created by: sangeethnawa
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "service")
public class GeneralService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String serviceKey;
    private String service;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
