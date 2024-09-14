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
    private final List<String> sizes = List.of("Pequeño", "Mediano", "Grande");
    private final List<String> colors = List.of("Marron Claro", "Negro", "Blanco");
    private final List<String> breeds = List.of("Salchicha", "Collie", "Labrador");
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
        registerPet("Mila", 7, "Perro", "Pequeño", "Marron Claro", "Salchicha", "Hembra", "Me llamo Mila y soy una perra tranquila y cariñosa. Me gusta mucho acurrucarme en mi camita, pero no te preocupes, también estoy lista para caminar contigo y disfrutar del aire fresco. Soy muy dulce y me llevo bien con todos, tanto con humanos como con otros animales. Si buscas una compañera fiel y amorosa, ¡aquí estoy yo! Estoy esperando por ese hogar especial donde pueda sentirme segura y querida.");
        registerPet("Calu", 1, "Perro", "Mediano", "Chocolate", "Mestizo", "Hembra", "¡Hola! Soy Calu, un perrito lleno de energía y siempre con ganas de jugar. Me encanta correr al aire libre y explorar cada rincón del jardín, pero también disfruto de las tardes tranquilas a tu lado. Soy muy cariñoso y leal, y siempre estaré ahí para recibirte con mi mejor sonrisa y mover la cola de felicidad. Estoy buscando una familia que me quiera y me cuide tanto como yo la cuidaré. ¡Prometo ser tu mejor compañero de aventuras!");
        registerPet("Boni", 3, "Perro", "Grande", "Blanco", "Mestizo", "Macho", "¡Hey! Soy Boni, un perrito juguetón y lleno de amor. Siempre estoy listo para una buena sesión de juego o una caminata larga por el parque. Me encanta estar rodeado de personas y recibir todo el cariño que puedan darme, y yo te lo devolveré con creces. También soy muy obediente y me encanta aprender cosas nuevas. Estoy buscando una familia activa que me haga parte de su vida. ¿Nos damos una oportunidad?");
        registerPet("Tomy", 7, "Gato", "Mediano", "Blanco", "Mestizo", "Macho", "Hola, soy Tomy, un gato independiente pero muy mimoso cuando agarro confianza. Me encanta observar todo desde un lugar alto y tranquilo, pero si me das una caricia, te demostraré lo mucho que me gustan los mimos. No necesito mucho para ser feliz, solo un hogar cálido y alguien con quien compartir mi día. Si buscas un compañero calmado, con quien disfrutar tardes relajadas, ¡creo que seremos el equipo perfecto!"

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
        pet.setImage("assets/"+pet.getName()+".jpeg");
        pet.setDescription("Descripción de " + pet.getName()); // Ajustar según sea necesario

        petDao.save(pet);
        logger.info("Pet registered: " + pet.getName());
    }

    public void registerPet(String name, int age, String type, String size, String color, String breed, String gender, String description) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setAge(age);
        pet.setType(type);
        pet.setSize(size);
        pet.setColor(color);
        pet.setBreed(breed);
        pet.setGender(gender);
        pet.setImage("assets/"+pet.getName()+".jpeg");
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

    @Override
    public int getOrder() {
        return 0;
    }
}