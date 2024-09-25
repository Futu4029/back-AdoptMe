package com.unq.adopt_me.factory;

import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.entity.pet.Pet;

public class PetFactory {
    public static Pet anyPet(){
        return new Pet("Boni", 1, "Perro", "Pequeño", "Marrón", "Calle", "Hembra", "image.png", "descripcion de boni");
    }

    public static PetDto anyPetDto(){
        return new PetDto(anyPet());
    }
    public static PetDto petDtoWithInvalidatedValues(){
        return new PetDto("Mila", -7, null, null, null,null, null, null,null);
    }

    public static PetDto petDtoWithNegativeAge(){
        return new PetDto("Boni", -1, "Perro", "Pequeño", "Marrón", "Calle", "Hembra", "image.png", "descripcion de boni");
    }
    public static PetDto petDtoWithNoDescription(){
        return new PetDto("Boni", 1, "Perro", "Pequeño", "Marrón", "Calle", "Hembra", "image.png", null);
    }
    public static PetDto petDtoWithNoType(){
        return new PetDto("Boni", 1, null, "Pequeño", "Marrón", "Calle", "Hembra", "image.png", "descripcion de boni");
    }

    public static PetDto petDtoWithNoSize(){
        return new PetDto("Boni", 1, "Perro", null, "Marrón", "Calle", "Hembra", "image.png", "descripcion de boni");
    }

    public static PetDto petDtoWithNoGender(){
        return new PetDto("Boni", 1, "Perro", "Pequeño", "Marrón", "Calle", null, "image.png", "descripcion de boni");
    }

    public static PetDto petDtoWithNoImage(){
        return new PetDto("Boni", 1, "Perro", "Pequeño", "Marrón", "Calle", "Hembra", null, "descripcion de boni");
    }

    public static PetDto petDtoWithNoName(){
        return new PetDto(null, 1, "Perro", "Pequeño", "Marrón", "Calle", "Hembra", "image.png", "descripcion de boni");
    }
}
