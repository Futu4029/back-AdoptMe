package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionDao extends JpaRepository<Adoption, Long> {

    List<Adoption> findAllByOwner_Id(Long owner_Id);
    boolean existsByOwnerAndPet(User owner, Pet pet);

}
