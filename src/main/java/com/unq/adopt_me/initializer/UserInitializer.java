package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.RoleDao;
import com.unq.adopt_me.entity.security.Role;
import com.unq.adopt_me.entity.user.Adopter;
import com.unq.adopt_me.entity.user.Owner;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.service.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserInitializer  {

    protected final Log logger = LogFactory.getLog(getClass());

    public static final String PASS = "Asda1234";

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleDao roleDao;

    private final List<String> emails = List.of("juan.perez@gmail.com", "maria.lopez@gmail.com", "carlos.gomez@gmail.com", "laura.martinez@gmail.com", "fernando.diaz@gmail.com");
    private final List<String> names = List.of("Juan", "María", "Carlos", "Laura", "Fernando");
    private final List<String> surNames = List.of("Pérez", "López", "Gómez", "Martínez", "Díaz");
    private final List<String> localities = List.of("Lanús", "Avellaneda", "Quilmes", "Berazategui", "Lomas de Zamora");
    private final List<String> provinces = List.of("Buenos Aires");

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitialization() {
        initializeRoles();
        for (int i = 0; i < emails.size(); i++) {
            registerOwner();
            registerAdopter();
        }
        registerAdopter("test.user@gmail.com", "María","López", "Quilmes", "Buenos Aires", PASS);
    }

    private void initializeRoles() {
        roleDao.save(new Role("ADMIN"));
        roleDao.save(new Role("USER"));
    }

    public void registerOwner() {
        Owner user = new Owner();
        setData(user);

        userService.createUser(user);
        logger.info("Owner registered: " + user.getEmail());
    }

    public void registerAdopter() {
        Adopter user = new Adopter();
        setData(user);
        userService.createUser(user);
        logger.info("Adopter registered: " + user.getEmail());
    }
    public void registerAdopter(String email, String name, String surName, String localities, String provinces, String password) {
        Adopter user = new Adopter();
        user.setEmail(email);
        user.setName(name);
        user.setSurName(surName);
        user.setLocality(localities);
        user.setProvince(provinces);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(roleDao.findByName("ADMIN").isPresent() ? roleDao.findByName("ADMIN").get() : null));

        userService.createUser(user);
        logger.info("Adopter registered: " + user.getEmail());
    }

    private void setData(User user) {
        user.setEmail(emails.get(getRandomIndex(emails.size())));
        user.setName(names.get(getRandomIndex(names.size())));
        user.setSurName(surNames.get(getRandomIndex(surNames.size())));
        user.setLocality(localities.get(getRandomIndex(localities.size())));
        user.setProvince(provinces.get(getRandomIndex(provinces.size())));
        user.setPassword(PASS);
        user.setRoles(Collections.singletonList(roleDao.findByName("ADMIN").isPresent() ? roleDao.findByName("ADMIN").get() : null));
    }

    private int getRandomIndex(int size) {
        return (int) (Math.random() * size);
    }

}