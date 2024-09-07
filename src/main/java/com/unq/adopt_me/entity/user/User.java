package com.unq.adopt_me.entity.user;

import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @Column(nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    private String surName;

    @NotBlank
    private String locality;
    @NotBlank
    private String province;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Adoption> adoptions;


    public User (){}
}
