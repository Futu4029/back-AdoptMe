package com.unq.adopt_me.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralResponse<T> {

    private String message;
    @JsonProperty
    private T data;

    // Constructor privado para el Builder
    private GeneralResponse(Builder<T> builder) {
        this.message = builder.message;
        this.data = builder.data;
    }

    // Static inner Builder class
    public static class Builder<T> {
        private String message;
        private T data;

        // Método para establecer el mensaje
        public Builder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        // Método para establecer los datos
        public Builder<T> withData(T data) {
            this.data = data;
            return this;
        }

        // Método para construir la instancia de GeneralResponse
        public GeneralResponse<T> build() {
            return new GeneralResponse<>(this);
        }
    }
}
