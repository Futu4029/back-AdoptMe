package com.unq.adopt_me.dto.application;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.ApplicationStatus;

public class ApplicationDto {

    public AdoptionResponse adoption;

    public String applicationStatus;

    public User user;

    public ApplicationDto(Application application) {
        this.adoption = new AdoptionResponse(application.getAdoption());
        this.applicationStatus = application.getApplicationStatus().getDisplayName();
        this.user = application.getAdopter();
    }
}
