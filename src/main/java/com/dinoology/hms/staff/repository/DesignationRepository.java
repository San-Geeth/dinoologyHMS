package com.dinoology.hms.staff.repository;

import com.dinoology.hms.staff.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/16/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    boolean existsByDesignationKey(String key);
}
