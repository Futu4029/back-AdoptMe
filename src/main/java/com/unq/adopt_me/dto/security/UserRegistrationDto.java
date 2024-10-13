package com.unq.adopt_me.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserRegistrationDto {

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String surName;
    @NotBlank
    private String locality;
    @NotBlank
    private String province;


    @Column(columnDefinition = "MEDIUMTEXT")
    private String image;

    @NotNull
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    private String password;  // Campo para la contraseña

    private List<Role> roles = new ArrayList<>();

    private List<Adoption> adoptions;

    private List<Application> applications;

    private List<UUID> blackList = new ArrayList<>();

    @NotNull
    private Boolean livesOnHouse;
    @NotNull
    private Boolean isPropertyOwner;
    @NotNull
    private Boolean canHavePetsOnProperty;
    @NotNull
    private Boolean haveAnyPetsCastrated;
    @NotBlank
    private String whatToDoIfHolydays;
    @NotBlank
    private String whatToDoIfMoving;
    @NotNull
    private Boolean compromiseAccepted;
}
