package com.unq.adopt_me.dto.user;

import com.unq.adopt_me.entity.user.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private double distance;
}
