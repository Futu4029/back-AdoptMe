package com.unq.adopt_me.entity.user;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ADOPTER")
public class Adopter extends User{

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adoption> adoptions;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;  // Relación con múltiples aplicaciones

}
