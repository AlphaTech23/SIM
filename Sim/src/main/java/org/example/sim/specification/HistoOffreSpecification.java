package org.example.sim.specification;

import org.example.sim.models.HistoOffre;
import org.example.sim.models.dto.HistoOffreSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class HistoOffreSpecification {

    public static Specification<HistoOffre> filter(HistoOffreSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idoffreOffres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdoffreOffres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idoffreOffres"), object.getIdoffreOffres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idstatutoffreStatutOffres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdstatutoffreStatutOffres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idstatutoffreStatutOffres"), object.getIdstatutoffreStatutOffres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=date | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDate()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("date"), object.getDate()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("date"), object.getDateMin(), object.getDateMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=remarque | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("remarque"), object.getRemarque(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
