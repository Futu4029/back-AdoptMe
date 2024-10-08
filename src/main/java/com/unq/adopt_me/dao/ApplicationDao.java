package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.adoption.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationDao extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
}
