package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
