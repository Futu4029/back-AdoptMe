package com.unq.adopt_me.initializer;

import com.unq.adopt_me.dao.PetDao;
import com.unq.adopt_me.entity.pet.Pet;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PetInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private PetDao petDao;

    private final List<String> names = List.of("Mila", "Calu", "Boni", "Tomy");
    private final List<String> types = List.of("Perro", "Gato");
    private final List<String> sizes = List.of("Pequeño", "Mediano", "Grande");
    private final List<String> colors = List.of("Marron Claro", "Negro", "Blanco");
    private final List<String> breeds = List.of("Salchicha", "Collie", "Labrador", "Mestizo");
    private final List<String> gender = List.of("Macho", "Hembra");

    @PostConstruct
    public void initialize() {
        startInitialization();
    }

    private void startInitializationRandom() {
        for (int i = 0; i < 3; i++) {
            registerPetRandom(i);
        }
    }

    private void startInitialization() {
        registerPet(
                "Mila", 7, "Perro", "Pequeño", "Marron Claro", "Salchicha", "Hembra",
                List.of("assets/Mila.jpeg", "assets/Mila2.jpeg", "assets/Mila3.jpeg"),
                "Me llamo Mila y soy una perra tranquila y cariñosa. Me gusta mucho acurrucarme en mi camita..."
        );
        registerPet(
                "Calu", 1, "Perro", "Mediano", "Chocolate", "Mestizo", "Hembra",
                List.of("assets/Calu.jpeg", "assets/Calu2.jpeg" , "assets/Calu3.jpeg"),
                "¡Hola! Soy Calu, un perrito lleno de energía y siempre con ganas de jugar..."
        );
        registerPet(
                "Boni", 3, "Perro", "Grande", "Blanco", "Mestizo", "Macho",
                List.of("assets/Boni.jpeg", "assets/Boni2.jpeg",  "assets/Boni3.jpeg",  "assets/Boni4.jpeg"),
                "¡Hey! Soy Boni, un perrito juguetón y lleno de amor..."
        );
        registerPet(
                "Beni", 3, "Perro", "Pequeño", "Blanco", "Mestizo", "Macho",
                List.of("assets/Beni.jpeg"),
                "Hola, soy Beni, un perro independiente pero muy mimoso cuando agarro confianza..."
        );
        registerPet(
                "Manchita", 7, "Perro", "Mediano", "Blanco y Negro", "Mestizo", "Hembra",
                List.of("assets/Manchita.jpeg"),
                "Hola, soy Manchi"
        );
        registerPet(
                "Tirry", 7, "Perro", "Mediano", "Blanco", "Caniche", "Macho",
                List.of("assets/Tirry.jpeg"),
                "Hola, soy Tirry"
        );
        registerPet(
                "Tomy", 5, "Gato", "Mediano", "Blanco", "Mestizo", "Macho",
                List.of("assets/Tomy.jpeg"),
                "Hola, soy Tirry"
        );

    }

    public void registerPetRandom(int i) {
        Pet pet = new Pet();
        pet.setName(names.get(i));
        pet.setAge(getRandomAge());
        pet.setType(types.get(0));
        pet.setSize(sizes.get(i));
        pet.setColor(colors.get(getRandomIndex(colors.size())));
        pet.setBreed(breeds.get(i));
        pet.setGender(gender.get(getRandomIndex(gender.size())));
        pet.setImages(List.of("assets/" + pet.getName() + ".jpeg", "assets/" + pet.getName() + ".jpeg", "assets/" + pet.getName() + ".jpeg"));
        pet.setDescription("Descripción de " + pet.getName());
        petDao.save(pet);
        logger.info("Pet registered: " + pet.getName());
    }

    public void registerPet(String name, int age, String type, String size, String color, String breed, String gender, List<String> images, String description) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setAge(age);
        pet.setType(type);
        pet.setSize(size);
        pet.setColor(color);
        pet.setBreed(breed);
        pet.setGender(gender);
        pet.setImages(images);
        pet.setDescription(description);

        petDao.save(pet);
        logger.info("Pet registered: " + pet.getName());
    }

    private int getRandomIndex(int size) {
        return new Random().nextInt(size);
    }

    private int getRandomAge() {
        return new Random().nextInt(15) + 1; // Edad aleatoria entre 1 y 15 años
    }

}
