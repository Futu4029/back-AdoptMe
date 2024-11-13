package com.unq.adopt_me.util.filterhandler;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.security.CustomUserDetails;
import com.unq.adopt_me.util.filterhandler.adoptionfilters.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class AdoptionFilterManager {

    private static AdoptionFilterManager instance;

    private final List<FilterHandler> handlers;

    private Predicate predicate;

    public static synchronized AdoptionFilterManager getInstance() {
        if (instance == null) {
            instance = new AdoptionFilterManager(List.of(
                    new PetTypeFilter(),
                    new PetSizeFilter(),
                    new PetAgeFilter(),
                    new PetGenderFilter(),
                    new AdoptionStatusFilter()
            ));
        }
        return instance;
    }

    public AdoptionFilterManager(List<FilterHandler> handlers) {
        this.handlers = handlers;
    }

    public Predicate applyFilters(CriteriaBuilder cb, Root<Adoption> root, List<String> values) {
        predicate = cb.conjunction();
        values.forEach(value -> handlers.forEach(handler -> handleFilters(cb, root, predicate, value, handler)));
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Evita traerme las adopciones del mismo owner.
        return predicate;
    }

    public void handleFilters(CriteriaBuilder cb, Root<Adoption> root, Predicate predicate, String value, FilterHandler handler){
        if(handler.canHandle(value)){
            this.predicate = cb.and(predicate, handler.applyFilter(cb, root, value));
        }
    }
}