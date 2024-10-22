package com.unq.adopt_me.dto.application;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.ApplicationStatus;

public class ApplicationDto {

    public Adoption adoption;

    public ApplicationStatus applicationStatus;

    public User user;

    public ApplicationDto(Application application) {
        this.adoption = application.getAdoption();
        this.applicationStatus = application.getApplicationStatus();
        this.user = application.getAdopter();
    }
}
