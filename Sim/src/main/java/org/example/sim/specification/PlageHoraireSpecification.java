package org.example.sim.specification;

import org.example.sim.models.PlageHoraire;
import org.example.sim.models.dto.PlageHoraireSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class PlageHoraireSpecification {

    public static Specification<PlageHoraire> filter(PlageHoraireSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=jourDebut | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getJourDebut()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("jourDebut"), object.getJourDebut()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("jourDebut"), object.getJourDebutMin(), object.getJourDebutMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=jourFin | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getJourFin()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("jourFin"), object.getJourFin()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("jourFin"), object.getJourFinMin(), object.getJourFinMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
