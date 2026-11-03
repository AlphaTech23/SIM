package org.example.sim.specification;

import org.example.sim.models.ChoixHoraireDemandeur;
import org.example.sim.models.dto.ChoixHoraireDemandeurSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ChoixHoraireDemandeurSpecification {

    public static Specification<ChoixHoraireDemandeur> filter(ChoixHoraireDemandeurSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idrendezvousRendezVous | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdrendezvousRendezVous()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idrendezvousRendezVous"), object.getIdrendezvousRendezVous()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateChoix | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateChoix()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateChoix"), object.getDateChoix()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateChoix"), object.getDateChoixMin(), object.getDateChoixMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
