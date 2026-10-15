package org.example.sim.specification;

import org.example.sim.models.Sujet;
import org.example.sim.models.dto.SujetSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class SujetSpecification {

    public static Specification<Sujet> filter(SujetSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=libele | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("libele"), object.getLibele(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=description | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("description"), object.getDescription(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nom"), object.getNom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=prenom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("prenom"), object.getPrenom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=fonction | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("fonction"), object.getFonction(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=telephone | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("telephone"), object.getTelephone(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=email | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("email"), object.getEmail(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=whatsapp | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("whatsapp"), object.getWhatsapp(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=adresse | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("adresse"), object.getAdresse(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
