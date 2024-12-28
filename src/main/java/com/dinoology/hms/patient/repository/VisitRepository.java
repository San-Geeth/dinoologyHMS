package com.dinoology.hms.patient.repository;

import com.dinoology.hms.patient.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/28/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
