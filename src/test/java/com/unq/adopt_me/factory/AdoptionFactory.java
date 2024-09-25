package com.unq.adopt_me.factory;

import com.unq.adopt_me.dto.adoption.AdoptionRequest;
import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.util.AdoptionStatus;

import static com.unq.adopt_me.factory.PetFactory.*;
import static com.unq.adopt_me.factory.UserFactory.anyOwner;

public class AdoptionFactory {
    public static Adoption anyAdoption(){
        return new Adoption(anyPet(), anyOwner(), AdoptionStatus.OPEN);
    }

    public static AdoptionRequest anyAdoptionRequest(){
        return new AdoptionRequest(anyPetDto(), "1");
    }

    public static AdoptionRequest anyAdoptionWithPet_(PetDto petDto){
        return new AdoptionRequest(petDto, "1");
    }

    public static AdoptionRequest adoptionRequestWithWrongParameter(){
        return new AdoptionRequest(petDtoWithInvalidatedValues(), "1");
    }
}
