package com.unq.adopt_me.dao.specifications;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.filterhandler.AdoptionFilterManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class AdoptionSpecifications {

    static AdoptionFilterManager filterManager = AdoptionFilterManager.getInstance();

    public static Specification<Adoption> withFilters(String type, String age, String size, String gender, String status, User user) {
        return (Root<Adoption> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate;

            predicate = filterManager.applyFilters(cb, root, Arrays.asList(type, size, age, gender, status));
            // Base filters
            //Filtrado para adopciones propias
            predicate = cb.and(predicate, cb.notEqual(root.get("owner").get("id"), user.getId()));

            //filtrado para todas las adopciones que esta en la blacklist
            for (UUID uuid : user.getBlackList()){
                predicate = cb.and(predicate, cb.notEqual(root.get("id"), uuid));
            }
            return predicate;
        };
    }

}