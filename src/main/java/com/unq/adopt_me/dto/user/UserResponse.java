package com.unq.adopt_me.dto.user;

import com.unq.adopt_me.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {


    private String email;
    private String name;
    private String surName;
    private String locality;
    private String province;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.surName = user.getSurName();
        this.locality = user.getLocality();
        this.province = user.getProvince();
    }
}
