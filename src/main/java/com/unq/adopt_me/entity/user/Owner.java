package com.unq.adopt_me.entity.user;

import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.*;

import java.util.List;


@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adoption> adoptions;
}
