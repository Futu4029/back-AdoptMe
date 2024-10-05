package com.unq.adopt_me.util.canHandle;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.PetSize;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PetSizeFilterHandler extends PetFilterHandler {

    @Override
    public Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        PetSize petSize = PetSize.getEnum(value);
        return cb.equal(root.get("pet").get("size"), petSize.getDisplayName());
    }
}