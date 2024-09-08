package com.unq.adopt_me.entity.adoption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="adoptions")
@AllArgsConstructor
@RequiredArgsConstructor
public class Adoption {

    @Id
    @Column(nullable = false, unique = true)
    // TODO(CAMBIAR A UUID)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //  O otro tipo de generación, según tu configuración
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)  // Relación con el adoptante (User)
    @JoinColumn(name = "adopter_user_id", referencedColumnName = "id", nullable = true)
    private User adopter;

    @OneToOne(fetch = FetchType.EAGER)  // Relación con Pet
    @JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)  // Relación con User (Owner)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @NotBlank
    private String status;

    @OneToMany(mappedBy = "adoption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;  // Relación con múltiples aplicaciones


}
