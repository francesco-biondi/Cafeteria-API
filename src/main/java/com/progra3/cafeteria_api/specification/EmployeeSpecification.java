package com.progra3.cafeteria_api.specification;

import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecification {

    public static Specification<Employee> filterBy(
            String name,
            String lastName,
            String dni,
            String email,
            String phoneNumber,
            Role role,
            Boolean deleted
    ){
        return(root, query, builder) -> {
            var predicates = new java.util.ArrayList<Predicate>();

            if(name != null && !name.isBlank()){
                predicates.add(builder.like(
                        builder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if(lastName != null && !lastName.isBlank()){
                predicates.add(builder.like(
                        builder.lower(root.get("lastName")),
                        "%" + lastName.toLowerCase() + "%"
                ));
            }

            if(dni != null && !dni.isBlank()){
                predicates.add(builder.equal(root.get("dni"), dni));
            }

            if(email != null && !email.isBlank()){
                predicates.add(builder.like(
                        builder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"
                ));
            }

            if(phoneNumber != null && !phoneNumber.isBlank()){
                predicates.add(builder.equal(root.get("phoneNumber"), phoneNumber));
            }

            if(role != null){
                predicates.add(builder.equal(root.get("role"), role));
            }

            if(deleted != null){
                predicates.add(builder.equal(root.get("deleted"), deleted));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
