package com.dinoology.hms.user.repository;

import com.dinoology.hms.user.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by: sange
 * Date:2024 12/14/2024
 * Copyright © 2024 DinooLogy
 */
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    boolean existsByType(String type);
}
