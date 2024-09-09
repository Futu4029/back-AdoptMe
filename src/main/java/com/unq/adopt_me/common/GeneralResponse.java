package com.unq.adopt_me.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralResponse {

    private String message;

    private Object data;
    // Constructor privado para el Builder
    private GeneralResponse(Builder builder) {
        this.message = builder.message;
        this.data = builder.data;
    }

    // Static inner Builder class
    public static class Builder {
        private String message;
        private Object data;

        // Método para establecer el mensaje
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        // Método para establecer los datos
        public Builder withData(Object data) {
            this.data = data;
            return this;
        }

        // Método para construir la instancia de GeneralResponse
        public GeneralResponse build() {
            return new GeneralResponse(this);
        }
    }
}
