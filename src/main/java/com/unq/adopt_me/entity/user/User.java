package com.unq.adopt_me.entity.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.unq.adopt_me.entity.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    private String password;  // Campo para la contraseña

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                        inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
    private List<Role> roles = new ArrayList<>();

    public User (){}

    public User(String email, String name, String surName, String locality, String province) {
        this.email = email;
        this.name = name;
        this.surName = surName;
        this.locality = locality;
        this.province = province;
    }
}
