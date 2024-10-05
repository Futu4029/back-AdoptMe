package com.unq.adopt_me.util.canHandle;

import ch.qos.logback.core.util.StringUtil;
import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class PetFilterHandler {
    boolean canHandle(String filterKey){
        return StringUtil.notNullNorEmpty(filterKey);
    }

    abstract Predicate applyFilter(CriteriaBuilder cb, Root<Adoption> root, String value);
}