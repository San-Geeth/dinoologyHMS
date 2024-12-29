package com.dinoology.hms.staff.repository;

import com.dinoology.hms.staff.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/28/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    boolean existsByNic(String nic);
    @Query("SELECT d FROM Doctor d WHERE d.id=:did")
    Doctor findDoctorByPK(@Param("did") Integer did);
}
