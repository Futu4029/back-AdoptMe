package com.unq.adopt_me.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unq.adopt_me.dto.security.UserRegistrationDto;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.adoption.Application;
import com.unq.adopt_me.entity.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;
    @NotBlank
    private String telefono;
    @NotBlank
    private String name;
    @NotBlank
    private String surName;
    @NotBlank
    private String locality;
    @NotBlank
    private String province;

    @Embedded
    private Localization localization;

    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    @JsonIgnore
    private String image;

    @JsonIgnore
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    private String password;  // Campo para la contraseña

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                        inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adoption> adoptions;

    @JsonIgnore
    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_blacklist", joinColumns = @JoinColumn(name = "user_id"))
    private List<UUID> blackList = new ArrayList<>();

    private Boolean livesOnHouse;
    private Boolean isPropertyOwner;
    private Boolean canHavePetsOnProperty;
    private Boolean haveAnyPetsCastrated;
    private String whatToDoIfHolydays;
    private String whatToDoIfMoving;
    private Boolean compromiseAccepted;

    public User (){}

    public User(String email, String name, String telefono, String surName, String locality, String province, String image) {
        this.email = email;
        this.name = name;
        this.telefono = telefono;
        this.image = image;
        this.surName = surName;
        this.locality = locality;
        this.province = province;
    }

    public User(String email, String name, String telefono, String surName, String locality, String province, List<Adoption> adoptionList) {
        this.email = email;
        this.name = name;
        this.telefono = telefono;
        this.surName = surName;
        this.locality = locality;
        this.province = province;
        this.adoptions = adoptionList;
    }
    public User(String email, String name, String surName, String locality, String province, List<Adoption> adoptionList, List<Application> applications, Boolean livesOnHouse, Boolean isPropertyOwner, Boolean canHavePetsOnProperty, Boolean haveAnyPetsCastrated, String whatToDoIfHolydays, String whatToDoIfMoving, Boolean compromiseAccepted) {
        this.email = email;
        this.name = name;
        this.surName = surName;
        this.locality = locality;
        this.province = province;
        this.adoptions = adoptionList;
        this.applications = applications;
        this.livesOnHouse = livesOnHouse;
        this.isPropertyOwner = isPropertyOwner;
        this.canHavePetsOnProperty = canHavePetsOnProperty;
        this.haveAnyPetsCastrated = haveAnyPetsCastrated;
        this.whatToDoIfHolydays = whatToDoIfHolydays;
        this.whatToDoIfMoving = whatToDoIfMoving;
        this.compromiseAccepted = compromiseAccepted;
    }
    public User(UserRegistrationDto userRegistrationDto) {
        this.email = userRegistrationDto.getEmail();
        this.name = userRegistrationDto.getName();
        this.telefono = userRegistrationDto.getTelefono();
        this.surName = userRegistrationDto.getSurName();
        this.locality = userRegistrationDto.getLocality();
        this.province = userRegistrationDto.getProvince();
        this.image = userRegistrationDto.getImage();
        this.localization = userRegistrationDto.getLocalization();
        this.adoptions = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.livesOnHouse = userRegistrationDto.getLivesOnHouse();
        this.isPropertyOwner = userRegistrationDto.getIsPropertyOwner();
        this.canHavePetsOnProperty = userRegistrationDto.getCanHavePetsOnProperty();
        this.haveAnyPetsCastrated = userRegistrationDto.getHaveAnyPetsCastrated();
        this.whatToDoIfHolydays = userRegistrationDto.getWhatToDoIfHolydays();
        this.whatToDoIfMoving = userRegistrationDto.getWhatToDoIfMoving();
        this.compromiseAccepted = userRegistrationDto.getCompromiseAccepted();
    }
}
