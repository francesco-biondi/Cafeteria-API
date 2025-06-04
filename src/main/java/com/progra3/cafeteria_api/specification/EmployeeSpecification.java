package com.progra3.cafeteria_api.specification;

import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filterBy(
            String name,
            String lastName,
            String dni,
            String email,
            String phoneNumber,
            Role role,
            Boolean deleted,
            Long businessId
    ) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addLikeIgnoreCase(predicates, builder, root.get("name"), name);
            addLikeIgnoreCase(predicates, builder, root.get("lastName"), lastName);
            addLikeIgnoreCase(predicates, builder, root.get("dni"), dni);
            addLikeIgnoreCase(predicates, builder, root.get("email"), email);
            addLikeIgnoreCase(predicates, builder, root.get("phoneNumber"), phoneNumber);
            addEqual(predicates, builder, root.get("role"), role);
            addEqual(predicates, builder, root.get("deleted"), deleted);
            addEqual(predicates, builder, root.get("businessId"), businessId);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLikeIgnoreCase(List<Predicate> predicates,
                                          jakarta.persistence.criteria.CriteriaBuilder builder,
                                          jakarta.persistence.criteria.Path<String> path,
                                          String value) {
        if (value != null && !value.isBlank()) {
            predicates.add(builder.like(builder.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

    private static <T> void addEqual(List<Predicate> predicates,
                                     jakarta.persistence.criteria.CriteriaBuilder builder,
                                     jakarta.persistence.criteria.Path<T> path,
                                     T value) {
        if (value != null) {
            predicates.add(builder.equal(path, value));
        }
    }
}

