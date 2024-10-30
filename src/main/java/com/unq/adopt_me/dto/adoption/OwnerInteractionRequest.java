package com.unq.adopt_me.dto.adoption;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInteractionRequest {

    private String adoptionId;

    private Long adopterId;

    private Boolean status;

}
