package com.unq.adopt_me.entity.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="users")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Owner.class, name = "owner"),
        @JsonSubTypes.Type(value = Adopter.class, name = "adopter")})
@AllArgsConstructor
public abstract class User {

    @Id
    @Column(nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String surName;
    @NotBlank
    private String locality;
    @NotBlank
    private String province;

    public User (){}

    public User(String email, String name, String surName, String locality, String province) {
        this.email = email;
        this.name = name;
        this.surName = surName;
        this.locality = locality;
        this.province = province;
    }
}
