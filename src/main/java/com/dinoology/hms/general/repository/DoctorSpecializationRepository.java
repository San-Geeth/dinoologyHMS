package com.dinoology.hms.general.repository;

import com.dinoology.hms.general.model.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/29/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Integer> {
    boolean existsBySpecializationKey(String specializationKey);

    @Query("SELECT ds FROM DoctorSpecialization ds WHERE ds.id=:did")
    DoctorSpecialization getDoctorSpecializationById(@Param("did") Integer did);
}
