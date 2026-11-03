package org.example.sim.specification;

import org.example.sim.models.CiblesOffre;
import org.example.sim.models.dto.CiblesOffreSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class CiblesOffreSpecification {

    public static Specification<CiblesOffre> filter(CiblesOffreSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idmembreMembres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdmembreMembres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idmembreMembres"), object.getIdmembreMembres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateEnvoi | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateEnvoi()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateEnvoi"), object.getDateEnvoi()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateEnvoi"), object.getDateEnvoiMin(), object.getDateEnvoiMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
