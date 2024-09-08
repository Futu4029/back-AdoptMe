package com.unq.adopt_me.entity.adoption;

import com.unq.adopt_me.entity.user.Adopter;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Application {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adopter_id", nullable = false)
    private Adopter adopter;  // Un adopter puede tener muchas applications

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adoption_id", nullable = false)
    private Adoption adoption;  // Una adopción puede tener muchas applications


}
