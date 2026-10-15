package org.example.sim.specification;

import org.example.sim.models.Membre;
import org.example.sim.models.dto.MembreSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class MembreSpecification {

    public static Specification<Membre> filter(MembreSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idsecteurSecteurs | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdsecteurSecteurs()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idsecteurSecteurs"), object.getIdsecteurSecteurs()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=intitule | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("intitule"), object.getIntitule(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=logo | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("logo"), object.getLogo(), predicates);
            
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
