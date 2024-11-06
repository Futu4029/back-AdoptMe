package com.unq.adopt_me.dto.adoption;

import com.unq.adopt_me.dto.pet.PetDto;
import com.unq.adopt_me.dto.user.UserResponse;
import com.unq.adopt_me.entity.adoption.Adoption;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AdoptionResponse {

    private UUID id;
    private UserResponse adopterUser;
    private PetDto pet;
    private UserResponse owner;
    private String status;
    private double distance;

    public AdoptionResponse(Adoption adoption) {
        this.id = adoption.getId();
        this.adopterUser = adoption.getAdopter() != null ? new UserResponse(adoption.getAdopter()) : null;
        this.pet = new PetDto(adoption.getPet());
        this.owner = new UserResponse(adoption.getOwner());;
        this.status = adoption.getStatus();
    }
}
