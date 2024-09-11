package com.unq.adopt_me.entity.pet;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pets")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    private String name;
    private int age;
    private String type;
    private String size;
    private String color;
    private String breed;
    private String gender;
    private String image;
    private String description;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Adoption adoption;  // Relación inversa con las adopciones


}