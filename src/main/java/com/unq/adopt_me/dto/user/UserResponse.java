package com.unq.adopt_me.dto.user;

import com.unq.adopt_me.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private String name;
    private String surName;
    private String locality;
    private String province;
    private String image;
    private Boolean livesOnHouse;
    private Boolean isPropertyOwner;
    private Boolean canHavePetsOnProperty;
    private Boolean haveAnyPetsCastrated;
    private String whatToDoIfHolydays;
    private String whatToDoIfMoving;
    private Boolean compromiseAccepted;

    public UserResponse(User user) {
        this.name = user.getName();
        this.surName = user.getSurName();
        this.locality = user.getLocality();
        this.province = user.getProvince();
        this.image = user.getImage();
        this.livesOnHouse = user.getLivesOnHouse();
        this.isPropertyOwner = user.getIsPropertyOwner();
        this.canHavePetsOnProperty = user.getCanHavePetsOnProperty();
        this.haveAnyPetsCastrated = user.getHaveAnyPetsCastrated();
        this.whatToDoIfHolydays = user.getWhatToDoIfHolydays();
        this.whatToDoIfMoving = user.getWhatToDoIfMoving();
        this.compromiseAccepted = user.getCompromiseAccepted();
    }
}
