package com.unq.adopt_me.util.canHandle;

import com.unq.adopt_me.entity.adoption.Adoption;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PetFilterManager {

    private static PetFilterManager instance;

    private final List<PetFilterHandler> handlers;

    public static synchronized PetFilterManager getInstance() {
        if (instance == null) {
            instance = new PetFilterManager(List.of(
                    new PetTypeFilterHandler(),
                    new PetSizeFilterHandler(),
                    new PetAgeFilterHandler(),
                    new PetGenderFilterHandler()
            ));
        }
        return instance;
    }

    public PetFilterManager(List<PetFilterHandler> handlers) {
        this.handlers = handlers;
    }

    public Predicate applyFilters(CriteriaBuilder cb, Root<Adoption> root, List<String> values) {
        Predicate predicate = cb.conjunction();

        for (int i = 0; i < handlers.size(); i++) {
            String value = values.get(i);
            PetFilterHandler handler = handlers.get(i);
            if(handler.canHandle(value)){
                predicate = cb.and(predicate, handler.applyFilter(cb, root, value));
            }

        }

        return predicate;
    }
}