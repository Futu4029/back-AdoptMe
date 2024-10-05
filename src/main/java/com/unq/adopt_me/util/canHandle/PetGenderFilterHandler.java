package com.unq.adopt_me.util.canHandle;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.PetGender;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PetGenderFilterHandler extends PetFilterHandler {

    @Override
    public Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        PetGender petGender = PetGender.getEnum(value);
        return cb.equal(root.get("pet").get("gender"), petGender.getDisplayName());
    }
}