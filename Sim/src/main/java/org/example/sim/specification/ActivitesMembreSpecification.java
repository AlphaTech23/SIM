package org.example.sim.specification;

import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.dto.ActivitesMembreSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ActivitesMembreSpecification {

    public static Specification<ActivitesMembre> filter(ActivitesMembreSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=id | columnType=[serial] | isText=false | isNumeric=true");

            
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idmembreMembres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdmembreMembres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idmembreMembres"), object.getIdmembreMembres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idactiviteActivites | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdactiviteActivites()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idactiviteActivites"), object.getIdactiviteActivites()
                    )
                );
            }
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
