package org.example.sim.specification;

import org.example.sim.models.Offre;
import org.example.sim.models.dto.OffreSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class OffreSpecification {

    public static Specification<Offre> filter(OffreSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idtypeoffreTypeOffres | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdtypeoffreTypeOffres()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idtypeoffreTypeOffres"), object.getIdtypeoffreTypeOffres()
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=titreOffre | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("titreOffre"), object.getTitreOffre(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=descriptions | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("descriptions"), object.getDescriptions(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=conditions | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("conditions"), object.getConditions(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateCreation | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateCreation()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateCreation"), object.getDateCreation()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateCreation"), object.getDateCreationMin(), object.getDateCreationMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateFinValidite | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateFinValidite()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateFinValidite"), object.getDateFinValidite()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateFinValidite"), object.getDateFinValiditeMin(), object.getDateFinValiditeMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
