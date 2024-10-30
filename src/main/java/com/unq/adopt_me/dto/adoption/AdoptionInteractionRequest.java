package com.unq.adopt_me.dto.adoption;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionInteractionRequest {

    @Valid
    private String adoptionId;

    private Long userId;


}
