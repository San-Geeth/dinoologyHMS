package com.dinoology.hms.staff.repository;

import com.dinoology.hms.staff.model.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 8:28 PM
 */
@Repository
public interface StaffRepository extends JpaRepository<StaffMember, Integer> {
    boolean existsByNic(String nic);
}
