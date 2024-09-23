package com.unq.adopt_me.factory;

import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.Adopter;
import com.unq.adopt_me.entity.user.Owner;
import com.unq.adopt_me.entity.user.User;

import java.util.List;

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

}
