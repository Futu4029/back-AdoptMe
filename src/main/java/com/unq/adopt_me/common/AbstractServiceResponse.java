package com.unq.adopt_me.common;

public abstract class AbstractServiceResponse {

    public GeneralResponse generateResponse(String message, Object data){
        return new GeneralResponse.Builder().withMessage(message).withData(data).build();
    }
}