package com.dinoology.hms.user.repository;

import com.dinoology.hms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 5:59 PM
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    @Query("SELECT new User(u.id, u.username, u.userEmail, u.isActive) FROM User u WHERE u.id=:userId")
    User findByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive=false WHERE u.id=:userId")
    int deactivateUser(@Param("userId") Integer userId);
}
