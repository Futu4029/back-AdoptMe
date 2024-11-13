package com.unq.adopt_me.factory;

import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.entity.pet.Pet;

import java.util.Collections;
import java.util.List;

public class PetFactory {
    public static Pet anyPet() {
        return new Pet(
                "Boni",
                1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                List.of("video.mp4"),
                "Descripción de Boni"
        );
    }

    public static PetDto anyPetDto() {
        return new PetDto(anyPet());
    }

    public static PetDto petDtoWithInvalidatedValues() {
        return new PetDto(
                "Mila",
                -7,
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(), // Lista vacía para imágenes
                null
        );
    }

    public static PetDto petDtoWithNegativeAge() {
        return new PetDto(
                "Boni",
                -1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                "Descripción de Boni"
        );
    }

    public static PetDto petDtoWithNoDescription() {
        return new PetDto(
                "Boni",
                1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                null // Descripción nula
        );
    }

    public static PetDto petDtoWithNoType() {
        return new PetDto(
                "Boni",
                1,
                null, // Tipo nulo
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                "Descripción de Boni"
        );
    }

    public static PetDto petDtoWithNoSize() {
        return new PetDto(
                "Boni",
                1,
                "Perro",
                null, // Tamaño nulo
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                "Descripción de Boni"
        );
    }

    public static PetDto petDtoWithNoGender() {
        return new PetDto(
                "Boni",
                1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                null, // Género nulo
                List.of("image.png"),
                "Descripción de Boni"
        );
    }

    public static PetDto petDtoWithNoImage() {
        return new PetDto(
                "Boni",
                1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                Collections.emptyList(), // Lista de imágenes vacía
                "Descripción de Boni"
        );
    }

    public static PetDto petDtoWithNoName() {
        return new PetDto(
                null, // Nombre nulo
                1,
                "Perro",
                "Pequeño",
                "Marrón",
                "Calle",
                "Hembra",
                List.of("image.png"),
                "Descripción de Boni"
        );
    }
}
