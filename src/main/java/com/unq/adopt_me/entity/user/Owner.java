package com.unq.adopt_me.entity.user;

import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@DiscriminatorValue("OWNER")
@AllArgsConstructor
@NoArgsConstructor
public class Owner extends User{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adoption> adoptions;

    public Owner(String email, String name, String surName, String locality, String province, List<Adoption> adoptionList) {
        super(email, name, surName, locality, province);
        this.adoptions = adoptionList;
    }
}
