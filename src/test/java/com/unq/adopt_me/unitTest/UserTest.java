package com.unq.adopt_me.unitTest;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.factory.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    private final User user = UserFactory.anyAdopter();
    @Test
    void get_Email_Test() {
        Assertions.assertEquals("adopterExample@yopmail.com", user.getEmail());
        user.setEmail("ejemplo@ejemplo.com");
        Assertions.assertEquals("ejemplo@ejemplo.com", user.getEmail());
    }
    @Test
    void get_Name_Test() {
        Assertions.assertEquals("Example", user.getName());
        user.setName("ejemplo");
        Assertions.assertEquals("ejemplo", user.getName());
    }
    @Test
    void get_Surname_Test() {
        Assertions.assertEquals("Examplellido", user.getSurName());
        user.setSurName("ejemplo");
        Assertions.assertEquals("ejemplo", user.getSurName());
    }
    @Test
    void get_Address_Test() {
        Assertions.assertEquals("exampleLocality", user.getLocality());
        user.setLocality("ejemploDireccion");
        Assertions.assertEquals("ejemploDireccion", user.getLocality());
    }
    @Test
    void get_Password_Test() {
        Assertions.assertEquals("exampleProvince", user.getProvince());
        user.setProvince("exampleProvince!!");
        Assertions.assertEquals("exampleProvince!!", user.getProvince());
    }

    @Test
    void get_ID_Test() {
        Assertions.assertEquals(null, user.getId());
        user.setId(1L);
        Assertions.assertEquals(1L, user.getId());
    }
}
