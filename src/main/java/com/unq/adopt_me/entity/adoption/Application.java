package com.unq.adopt_me.entity.adoption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"adoption_id", "user_id"})
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User adopter;  // Un adopter puede tener muchas applications

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adoption_id", nullable = false)
    @JsonIgnore
    private Adoption adoption;  // Una adopción puede tener muchas applications

    public Application(Adoption adoption, User adopter) {
        this.adoption = adoption;
        this.adopter = adopter;
    }

    public Application() {
    }
}
