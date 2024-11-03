package com.unq.adopt_me.dto.pet;

import com.unq.adopt_me.entity.pet.Pet;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PetDto {

    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    private String name;

    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 30, message = "La edad debe ser menor o igual a 30")
    @NotNull(message = "La edad es obligatoria")
    private Integer age;

    @NotNull(message = "El tipo es obligatorio")
    private String type;

    @NotNull(message = "El tamaño es obligatorio")
    private String size;

    private String color;

    private String breed;

    @NotNull(message = "El género es obligatorio")
    private String gender;

    @NotEmpty(message = "La imágen es obligatoria")
    private List<String> images;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    public PetDto(String name, int age, String type, String size, String color, String breed, String gender, List<String> images, String description) {
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

    public PetDto(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.age = pet.getAge();
        this.type = pet.getType();
        this.size = pet.getSize();
        this.color = pet.getColor();
        this.breed = pet.getBreed();
        this.gender = pet.getGender();
        this.images = pet.getImages();
        this.description = pet.getDescription();
    }
}