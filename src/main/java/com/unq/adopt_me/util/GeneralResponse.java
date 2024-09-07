package com.unq.adopt_me.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralResponse {

    private String message;

    private Object data;
}
