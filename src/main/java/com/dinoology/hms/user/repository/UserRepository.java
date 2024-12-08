package com.dinoology.hms.user.repository;

import com.dinoology.hms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:59 PM
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
}
