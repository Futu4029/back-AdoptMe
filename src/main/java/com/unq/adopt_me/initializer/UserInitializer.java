package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.RoleDao;
import com.unq.adopt_me.entity.security.Role;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.service.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final List<String> telefonos = List.of("1156930347", "1147859630", "1147852369", "1145236987", "1122336655");
    private final List<String> surNames = List.of("Pérez", "López", "Gómez", "Martínez", "Díaz");
    private final List<String> localities = List.of("Lanús", "Avellaneda", "Quilmes", "Berazategui", "Lomas de Zamora");
    private final List<String> provinces = List.of("Buenos Aires");
    public static final String image = "UklGRqQrBgBXRUJQVlA4IGJpBACwbAmdASoABAAEPjEUiEKiISEjLDj8MGAGCWdukwNYYwZbzMn7fZlw3pdZapG53nhXHqcVXKHywrn07NnHgv6Ple89+mufnxif3e3H4j/teYv7R3/P+x+3/u9/tv/C/+P+s/fj/4fYb/YP9T6yv/X+5vvT/eL81vgd/Z/9t+3f/V+K3/yfuj7zv8r+Yf/I+Qr+7f8H/2evx/9f//8K/+f/9f///6fwR/0z/a////ke9X/9P3p/7vy0/4j/0fu//1/fT//f+/9wD/yf///mfFl/AP+T///YA/f/2x/Df14/xX7Ee+349+9f3T/Df5b/B/3D/2+rv418x/a/7b/jv85/cP/n/vPsQ+tf8H/Hfut6oPT/47/g/5f/Tf9X/E////i/Qn8f+2H4P+4/5H/cf3b9yfk7/c/4z/W/9f/EfvV7W/m37l/tf8L/of+J/jf3a+wX8Z/l397/tX+Q/2n94/b/6Zvnf+P/X+P/Hfuf/V8D/a/8e3/2/tv+R8P/tf+8/5H3r9j+//X+H/fcP+t/9F/Mf9P/l/5j/Y/97/s/vb8H8P/e/w/8X/b9v/9fzX/HwT7Mf+j/dz/9b/f/4H8Ue8L97H/s/9j/N+F/+v5X/Af/n2P/k/4T5Tf5H/Df8/4f9V/zn/ff3j9Z/nB9f9N+f/s/f/X+Zf7T/jf1X8T/W////3P9P9j/df4v/Qv1f7P/Vf+n//Kf/8H/6f/lv3X/YT/Bv+V/Sf7D/fX9H//f1n/c/+J+Hv7L/r/vf1f/T/af/if/Z/2n/H/rn/Zf+f8h/rP+H/UP8W+f5n/jT7R/8H/gP7q/wP7D/Qf/2//Xzv5T//U+z8b2f/X+8/+b/8z/4X+N/jP7v/Zf/Wf/l/5n/g/8h/z7/I/zP2D/Jf+X+0/tD/OX/VfzT+Z/9j9Nf/5/9Y/sT/y/+f4X+df9U/tH9c/2T/o/8j/0/9D/5f9Q/hb9v";

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitialization() {
        initializeRoles();
        for (int i = 0; i < emails.size(); i++) {
            registerUser();
        }
        registerUser("test.user@gmail.com", "María","1144778855","López", "Quilmes", "Buenos Aires", PASS);
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
    public void registerUser(String email, String name,  String telefono, String surName, String localities, String provinces, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setTelefono(telefono);
        user.setSurName(surName);
        user.setLocality(localities);
        user.setProvince(provinces);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(roleDao.findByName("ADMIN").isPresent() ? roleDao.findByName("ADMIN").get() : null));
        user.setImage(image);

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
    }

    private int getRandomIndex(int size) {
        return (int) (Math.random() * size);
    }

}