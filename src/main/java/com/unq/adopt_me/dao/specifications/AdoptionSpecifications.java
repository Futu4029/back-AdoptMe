package com.unq.adopt_me.dao.specifications;

import com.unq.adopt_me.entity.adoption.Adoption;
import com.unq.adopt_me.util.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AdoptionSpecifications {

    public static Specification<Adoption> withFilters(String type, String age, String size, String gender) {
        return (Root<Adoption> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (type != null) {
                PetType petType = PetType.getEnum(type);
                predicate = cb.and(predicate, cb.equal(root.get("pet").get("type"), petType.getDisplayName()));
            }
            if (age != null) {
                PetAge petAge = PetAge.getEnum(age);
                switch (petAge) {
                    case ADULT:
                        predicate = cb.and(predicate,
                                cb.greaterThanOrEqualTo(root.get("pet").get("age"), petAge.getMinAge()));
                        break;
                    default:
                        predicate = cb.and(predicate,
                                cb.greaterThanOrEqualTo(root.get("pet").get("age"), petAge.getMinAge()),
                                cb.lessThanOrEqualTo(root.get("pet").get("age"), petAge.getMaxAge()));
                        break;
                }
            }
            if (size != null) {
                PetSize sizeEnum = PetSize.getEnum(size);
                predicate = cb.and(predicate, cb.equal(root.get("pet").get("size"), sizeEnum.getDisplayName()));
            }
            if (gender != null) {
                PetGender genderEnum = PetGender.getEnum(gender);
                predicate = cb.and(predicate, cb.equal(root.get("pet").get("gender"), genderEnum.getDisplayName()));
            }

            predicate = cb.and(predicate, cb.equal(root.get("status"), AdoptionStatus.OPEN.name()));
            return predicate;
        };
    }
}