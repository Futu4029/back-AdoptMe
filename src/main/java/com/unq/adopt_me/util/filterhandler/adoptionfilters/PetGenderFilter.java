package com.unq.adopt_me.util.filterhandler.adoptionfilters;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.PetGender;
import com.unq.adopt_me.util.filterhandler.FilterHandler;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PetGenderFilter extends FilterHandler {

    @Override
    protected boolean canHandle(String filterKey) {
        return PetGender.getEnum(filterKey) != null;
    }

    @Override
    protected Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        PetGender petGender = PetGender.getEnum(value);
        return cb.equal(root.get("pet").get("gender"), petGender.getDisplayName());
    }
}