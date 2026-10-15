package org.example.sim.specification;

import org.example.sim.models.RendezVou;
import org.example.sim.models.dto.RendezVouSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class RendezVouSpecification {

    public static Specification<RendezVou> filter(RendezVouSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=iddemandeurMembres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIddemandeurMembres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("iddemandeurMembres"), object.getIddemandeurMembres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idreceveurSujetsMembres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdreceveurSujetsMembres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idreceveurSujetsMembres"), object.getIdreceveurSujetsMembres()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=contexte | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("contexte"), object.getContexte(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateRdv | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateRdv()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateRdv"), object.getDateRdv()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateRdv"), object.getDateRdvMin(), object.getDateRdvMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
