package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ApplicationDao extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    Boolean existsApplicationByAdopterAndAdoption(User adopter, Adoption adoption);
}
