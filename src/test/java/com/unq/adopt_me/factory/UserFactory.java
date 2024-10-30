package com.unq.adopt_me.factory;

import com.unq.adopt_me.entity.user.User;

import java.util.List;

public class UserFactory {
    public static User anyOwner(){
        return new User("ownerExample@yopmail.com", "Example", "Examplellido", "Bernal", "BsAs", List.of().toString(), "");
    }
    public static User anyAdopter(){
        return new User("adopterExample@yopmail.com", "Example", "Examplellido", "exampleLocality", "exampleProvince", List.of(), List.of(), true, true, true, true, "nada", "nada", false);
    }
}
