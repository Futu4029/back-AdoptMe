package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDao extends JpaRepository<Pet, Long> {

    Pet findPetByAdoption_Id(Long adoptionId);
}
