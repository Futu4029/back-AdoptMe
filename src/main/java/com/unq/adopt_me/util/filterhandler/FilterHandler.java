package com.unq.adopt_me.util.filterhandler;

import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class FilterHandler {

    protected abstract boolean canHandle(String filterKey);
    protected abstract Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value);

}