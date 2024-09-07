package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.entity.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserInitializer implements Ordered {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserDao userDao;

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
        for (int i = 0; i < emails.size(); i++) {
            registerUser();
        }
    }

    public void registerUser() {
        User user = new User();
        user.setEmail(emails.get(getRandomIndex(emails.size())));
        user.setName(names.get(getRandomIndex(names.size())));
        user.setSurName(surNames.get(getRandomIndex(surNames.size())));
        user.setLocality(localities.get(getRandomIndex(localities.size())));
        user.setProvince(provinces.get(getRandomIndex(provinces.size())));

        userDao.save(user);
        logger.info("User registered: " + user.getEmail());
    }

    private int getRandomIndex(int size) {
        return (int) (Math.random() * size);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}