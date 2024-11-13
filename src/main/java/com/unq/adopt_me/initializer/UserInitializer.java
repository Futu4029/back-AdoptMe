package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.RoleDao;
import com.unq.adopt_me.entity.security.Role;
import com.unq.adopt_me.entity.user.Localization;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.service.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
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
    private final List<String> telefonos = List.of("1156930347", "1147859630", "1147852369", "1145236987", "1122336655");
    private final List<String> surNames = List.of("Pérez", "López", "Gómez", "Martínez", "Díaz");
    private final List<String> localities = List.of("Lanús", "Avellaneda", "Quilmes", "Berazategui", "Lomas de Zamora");
    private final List<String> provinces = List.of("Buenos Aires");
    List<Localization> localizations = new ArrayList<>();




    @PostConstruct
    public void initialize() throws IOException {
        startInitialization();
    }

    private void startInitialization() throws IOException {
        initializeRoles();
        initializeLocalizations();
        for (int i = 0; i < emails.size(); i++) {
            registerUser();
        }
        registerUser("test.user@gmail.com", "María","1144778855","López", "Quilmes", "Buenos Aires", PASS, "profile1");
        registerUser("adopter.user@gmail.com", "Cami","1144778855","Pesci", "Quilmes", "Buenos Aires", PASS, "profile2");
    }

    private void initializeLocalizations() {
        localizations.add(new Localization(-34.6633,-58.3647));
        localizations.add(new Localization(-34.7096,-58.3910));
        localizations.add(new Localization(-34.7576,-58.4067));
        localizations.add(new Localization(-34.7435,-58.3832));
        localizations.add(new Localization(-34.7777,-58.3966));
        localizations.add(new Localization(-34.7982,-58.3937));
        localizations.add(new Localization(-34.8377,-58.3925));
        localizations.add(new Localization(-34.7201,-58.2546));
        localizations.add(new Localization(-34.7655,-58.2121));
        localizations.add(new Localization(-34.7894,-58.2789));
    }

    private void initializeRoles() {
        roleDao.save(new Role("ADMIN"));
        roleDao.save(new Role("USER"));
    }

    public void registerUser() {
        User user = new User();
        setData(user);
        userService.createUser(user);
        logger.info("User registered: " + user.getEmail());
    }
    public void registerUser(String email, String name, String telefono, String surName, String localities, String provinces, String password, String imageRoute) throws IOException {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setTelefono(telefono);
        user.setSurName(surName);
        user.setLocality(localities);
        user.setLocalization(new Localization(-34.70628, -58.2778));
        user.setProvince(provinces);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(roleDao.findByName("ADMIN").isPresent() ? roleDao.findByName("ADMIN").get() : null));
        InputStream imageStream = getClass().getResourceAsStream("/images/"+imageRoute+".jpg");
        if (imageStream == null) {
            throw new IOException("Image file not found in classpath: /images/"+imageRoute+".jpg");
        }
        byte[] imageBytes = imageStream.readAllBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);;
        user.setImage("data:image/jpeg;base64,"+base64Image);
        user.setLivesOnHouse(true);
        user.setIsPropertyOwner(true);
        user.setCanHavePetsOnProperty(true);
        user.setHaveAnyPetsCastrated(true);
        user.setWhatToDoIfHolydays("Me lo llevo");
        user.setWhatToDoIfMoving("Me lo llevo");
        user.setCompromiseAccepted(true); // o el valor que corresponda

        userService.createUser(user);
        logger.info("User registered: " + user.getEmail());
    }

    private void setData(User user) {
        user.setEmail(emails.get(getRandomIndex(emails.size())));
        user.setName(names.get(getRandomIndex(names.size())));
        user.setTelefono(telefonos.get(getRandomIndex(telefonos.size())));
        user.setSurName(surNames.get(getRandomIndex(surNames.size())));
        user.setLocality(localities.get(getRandomIndex(localities.size())));
        user.setProvince(provinces.get(getRandomIndex(provinces.size())));
        user.setPassword(PASS);
        user.setImage("");
        user.setRoles(Collections.singletonList(roleDao.findByName("ADMIN").isPresent() ? roleDao.findByName("ADMIN").get() : null));
        user.setLocalization(localizations.get(getRandomIndex(localizations.size())));
    }

    private int getRandomIndex(int size) {
        return (int) (Math.random() * size);
    }

}