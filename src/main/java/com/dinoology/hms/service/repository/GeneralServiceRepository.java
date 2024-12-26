package com.dinoology.hms.service.repository;

import com.dinoology.hms.service.model.GeneralService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Created by: sangeethnawa
 * Date:2024 12/26/2024
 * Copyright © 2024 DinooLogy
 */
@Repository
public interface GeneralServiceRepository extends JpaRepository<GeneralService, Integer> {
    boolean existsByServiceKey(String key);
}
