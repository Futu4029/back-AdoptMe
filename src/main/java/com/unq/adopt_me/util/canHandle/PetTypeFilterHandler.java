package com.unq.adopt_me.util.canHandle;

import ch.qos.logback.core.util.StringUtil;
import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.PetType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PetTypeFilterHandler extends PetFilterHandler {

    @Override
    public Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        PetType petType = PetType.getEnum(value);
        return cb.equal(root.get("pet").get("type"), petType.getDisplayName());
    }
}