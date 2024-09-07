package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.AdoptionDao;
import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.dao.UserDao;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.AdoptionStatus;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZAdoptionInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AdoptionDao adoptionDao;

    @Autowired
    private UserDao userDao;  // Para asociar el owner y el adopter

    @Autowired
    private PetDao petDao;  // Para asociar la mascota

    private final List<String> statuses = Arrays.stream(AdoptionStatus.values()).map(AdoptionStatus::getValue).collect(Collectors.toList());

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitialization() {
        for (int i = 0; i < 2; i++) {
            registerAdoption();
        }
    }

    public void registerAdoption() {
        Adoption adoption = new Adoption();
        Long number = 1L;
        User owner = userDao.findById(number).orElse(null);
        Pet pet = petDao.findById(number).orElse(null);
        number = number+1;
        if (owner != null && pet != null) {
            // Verificar si ya existe una adopción con el mismo owner y pet
            boolean exists = adoptionDao.existsByOwnerAndPet(owner, pet);
            if (!exists) {
                adoption.setOwner(owner);
                adoption.setPet(pet);
                adoption.setStatus(statuses.get(getRandomIndex(statuses.size())));

                adoptionDao.save(adoption);
                logger.info("Adoption registered: " + adoption.getId());
            } else {
                logger.warn("Adoption already exists for owner: " + owner.getId() + " and pet: " + pet.getId());
            }
        }
    }

    private int getRandomIndex(int size) {
        return new Random().nextInt(size);
    }

    private Long getRandomId(Long max) {
        return (long) (Math.random() * max) + 1;
    }

}