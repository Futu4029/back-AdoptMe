package com.unq.adopt_me.dao.specifications;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.filterhandler.AdoptionFilterManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdoptionSpecifications {

    static AdoptionFilterManager filterManager = AdoptionFilterManager.getInstance();

    public static Specification<Adoption> withFilters(String type, String age, String size, String gender, String status) {
        return (Root<Adoption> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate;

            predicate = filterManager.applyFilters(cb, root, Arrays.asList(type, size, age, gender, status));

            return predicate;
        };
    }
}