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

    public static AdoptionRequest anyAdoptionRequest(Long userId){
        return new AdoptionRequest(anyPetDto(), userId);
    }

    public static AdoptionRequest anyAdoptionWithPet_(PetDto petDto,Long userId){
        return new AdoptionRequest(petDto, userId);
    }

    public static AdoptionRequest adoptionRequestWithWrongParameter(Long userId){
        return new AdoptionRequest(petDtoWithInvalidatedValues(), userId);
    }
}
