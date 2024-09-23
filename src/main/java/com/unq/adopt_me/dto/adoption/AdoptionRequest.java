package com.unq.adopt_me.dto.adoption;

import com.unq.adopt_me.dto.pet.PetDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdoptionRequest {

    @Valid
    private PetDto petDto;

    @NotNull(message = "No tiene un userId asociado")
    @NotEmpty(message = "No tiene un userId asociado")
    private String userId;

}
