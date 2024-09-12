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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

    //private final List<String> statuses = Arrays.stream(AdoptionStatus.values()).map(AdoptionStatus::getValue).collect(Collectors.toList());

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitialization() {
        for (int i = 1; i < 4; i++) {
            registerAdoption(Integer.toString(i));
        }
    }

    public void registerAdoption(String i) {
        Adoption adoption = new Adoption();
        User owner = userDao.findById(Long.parseLong(i)).orElse(null);
        Pet pet = petDao.findById(Long.parseLong(i)).orElse(null);
        if (owner != null && pet != null) {
            // Verificar si ya existe una adopción con el mismo owner y pet
            boolean exists = adoptionDao.existsByOwnerAndPet(owner, pet);
            if (!exists) {

                adoption.setOwner(owner);
                adoption.setPet(pet);
                adoption.setStatus(AdoptionStatus.OPEN.getValue());

                adoptionDao.save(adoption);
                logger.info("Adoption registered: " + adoption.getId());
            } else {
                logger.warn("Adoption already exists for owner: " + owner.getId() + " and pet: " + pet.getId());
            }
        }
    }
}