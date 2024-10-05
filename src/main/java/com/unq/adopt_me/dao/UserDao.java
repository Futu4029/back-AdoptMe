package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional <User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
