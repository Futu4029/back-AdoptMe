package com.unq.adopt_me.util.filterhandler.adoptionfilters;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.AdoptionStatus;
import com.unq.adopt_me.util.filterhandler.FilterHandler;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AdoptionStatusFilter extends FilterHandler {
    @Override
    protected boolean canHandle(String filterKey) {
        return AdoptionStatus.getEnum(filterKey) != null;
    }

    @Override
    protected Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value) {
        AdoptionStatus adoptionStatus = AdoptionStatus.getEnum(value);
        return cb.equal(root.get("status"), adoptionStatus.getDisplayName());
    }
}
