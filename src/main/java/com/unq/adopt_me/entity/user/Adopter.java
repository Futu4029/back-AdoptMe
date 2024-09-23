package com.unq.adopt_me.entity.user;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("ADOPTER")
@NoArgsConstructor
public class Adopter extends User{

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adoption> adoptions;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;  // Relación con múltiples aplicaciones

    private Boolean livesOnHouse;
    private Boolean isPropertyOwner;
    private Boolean canHavePetsOnProperty;
    private Boolean haveAnyPetsCastrated;
    private String whatToDoIfHolydays;
    private String whatToDoIfMoving;
    private Boolean compromiseAccepted;

    public Adopter(String email, String name, String surName, String locality, String province, List<Adoption> adoptions, List<Application> applications, Boolean livesOnHouse, Boolean isPropertyOwner, Boolean canHavePetsOnProperty, Boolean haveAnyPetsCastrated, String whatToDoIfHolydays, String whatToDoIfMoving, Boolean compromiseAccepted) {
        super(email, name, surName, locality, province);
        this.adoptions = adoptions;
        this.applications = applications;
        this.livesOnHouse = livesOnHouse;
        this.isPropertyOwner = isPropertyOwner;
        this.canHavePetsOnProperty = canHavePetsOnProperty;
        this.haveAnyPetsCastrated = haveAnyPetsCastrated;
        this.whatToDoIfHolydays = whatToDoIfHolydays;
        this.whatToDoIfMoving = whatToDoIfMoving;
        this.compromiseAccepted = compromiseAccepted;
    }
}
