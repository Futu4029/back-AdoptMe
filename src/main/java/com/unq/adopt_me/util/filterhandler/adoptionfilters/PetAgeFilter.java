package com.unq.adopt_me.util.filterhandler.adoptionfilters;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.PetAge;
import com.unq.adopt_me.util.filterhandler.FilterHandler;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PetAgeFilter extends FilterHandler {

    @Override
    protected boolean canHandle(String filterKey) {
        return PetAge.getEnum(filterKey) != null;
    }

    @Override
    protected Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        PetAge petAge = PetAge.getEnum(value);
        if (petAge == PetAge.ADULT) {
            return cb.greaterThanOrEqualTo(root.get("pet").get("age"), petAge.getMinAge());
        } else {
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get("pet").get("age"), petAge.getMinAge()),
                    cb.lessThanOrEqualTo(root.get("pet").get("age"), petAge.getMaxAge())
            );
        }
    }
}