package com.dinoology.hms.patient.repository;

import com.dinoology.hms.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/27/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    boolean existsByContact(Long contact);
}
