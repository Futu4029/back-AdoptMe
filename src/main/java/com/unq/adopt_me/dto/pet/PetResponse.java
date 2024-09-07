package com.unq.adopt_me.dto.pet;

import com.unq.adopt_me.entity.pet.Pet;
import lombok.Data;

@Data
public class PetResponse {

    private Long id;
    private String name;
    private int age;
    private String type;
    private String size;
    private String color;
    private String breed;
    private String image;
    private String description;

    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.age = pet.getAge();
        this.type = pet.getType();
        this.size = pet.getSize();
        this.color = pet.getColor();
        this.breed = pet.getBreed();
        this.image = pet.getImage();
        this.description = pet.getDescription();
    }
}
