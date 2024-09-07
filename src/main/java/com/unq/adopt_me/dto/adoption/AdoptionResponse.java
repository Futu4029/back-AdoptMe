package com.unq.adopt_me.dto.adoption;

import com.unq.adopt_me.dto.pet.PetResponse;
import com.unq.adopt_me.dto.user.UserResponse;
import com.unq.adopt_me.entity.adoption.Adoption;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdoptionResponse {

    private Long id;
    private UserResponse adopterUser;
    private PetResponse pet;
    private UserResponse owner;
    private String status;

    public AdoptionResponse(Adoption adoption) {
        this.id = adoption.getId();
        this.adopterUser = adoption.getAdopter() != null ? new UserResponse(adoption.getAdopter()) : null;
        this.pet = new PetResponse(adoption.getPet());
        this.owner = new UserResponse(adoption.getOwner());;
        this.status = adoption.getStatus();
    }
}
