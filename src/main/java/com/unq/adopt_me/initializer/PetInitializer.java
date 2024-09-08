package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.entity.pet.Pet;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PetInitializer implements Ordered {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private PetDao petDao;

    private final List<String> names = List.of("Mila", "Calu", "Boni");
    private final List<String> types = List.of("Perro", "Gato");
    private final List<String> sizes = List.of("Chico", "Mediano", "Grande");
    private final List<String> colors = List.of("Marron Claro", "Negro", "Blanco");
    private final List<String> breeds = List.of("Salchicha", "Collie", "Labrador");

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitialization() {
        for (int i = 0; i < 3; i++) {
            registerPet(i);
        }
    }

    public void registerPet(int i) {
        Pet pet = new Pet();
        pet.setName(names.get(i));
        pet.setAge(getRandomAge());
        pet.setType(types.get(0));
        pet.setSize(sizes.get(i));
        pet.setColor(colors.get(getRandomIndex(colors.size())));
        pet.setBreed(breeds.get(i));
        pet.setDescription("Descripción de " + pet.getName()); // Ajustar según sea necesario

        petDao.save(pet);
        logger.info("Pet registered: " + pet.getName());
    }

    private int getRandomIndex(int size) {
        return new Random().nextInt(size);
    }

    private int getRandomAge() {
        return new Random().nextInt(15) + 1; // Edad aleatoria entre 1 y 15 años
    }

    @Override
    public int getOrder() {
        return 0;
    }
}