package com.unq.adopt_me.entity.pet;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String type;

    @NotNull
    private String size;

    private String color;

    private String breed;

    @NotNull
    private String gender;

    @ElementCollection
    @CollectionTable(name = "pet_images", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "image_url", columnDefinition = "MEDIUMTEXT")
    @NotNull
    private List<String> images;

    @NotNull
    @Column(length = 500)
    private String description;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonIgnore
    private Adoption adoption;  // Relación inversa con las adopciones

    public Pet(String name, Integer age, String type, String size, String color, String breed, String gender, List<String> images, List<String> videos, String description) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.size = size;
        this.color = color;
        this.breed = breed;
        this.gender = gender;
        this.images = images;
        this.description = description;
    }

    public Pet(PetDto petDto) {
        this.name = petDto.getName();
        this.age = petDto.getAge();
        this.type = petDto.getType();
        this.size = petDto.getSize();
        this.color = petDto.getColor();
        this.breed = petDto.getBreed();
        this.gender = petDto.getGender();
        this.images = petDto.getImages();
        this.description = petDto.getDescription();
    }
}