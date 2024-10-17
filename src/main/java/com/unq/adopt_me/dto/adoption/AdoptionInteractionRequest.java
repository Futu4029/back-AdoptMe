package com.unq.adopt_me.dto.adoption;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AdoptionInteractionRequest {

    @Valid
    private String adoptionId;

    private Long userId;
}
