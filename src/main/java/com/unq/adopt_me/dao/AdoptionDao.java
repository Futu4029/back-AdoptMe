package com.unq.adopt_me.dao;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.pet.Pet;
import com.unq.adopt_me.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdoptionDao extends JpaRepository<Adoption, UUID>, JpaSpecificationExecutor<Adoption> {

    List<Adoption> findAllByOwner_Id(Long owner_Id);
    List<Adoption> findAllByOwnerEmail(String email);
    boolean existsByOwnerAndPet(User owner, Pet pet);

}
