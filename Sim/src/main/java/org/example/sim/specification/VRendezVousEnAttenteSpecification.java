package org.example.sim.specification;

import org.example.sim.models.VRendezVousEnAttente;
import org.example.sim.models.dto.VRendezVousEnAttenteSearch;
import org.example.sim.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VRendezVousEnAttenteSpecification {

    public static Specification<VRendezVousEnAttente> filter(VRendezVousEnAttenteSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=idRendezVous | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdRendezVous()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idRendezVous"), object.getIdRendezVous()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idDemandeur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdDemandeur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idDemandeur"), object.getIdDemandeur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idDemandeur"), object.getIdDemandeurMin(), object.getIdDemandeurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=membreDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("membreDemandeur"), object.getMembreDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=logoDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("logoDemandeur"), object.getLogoDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=telephoneMembreDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("telephoneMembreDemandeur"), object.getTelephoneMembreDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=emailMembreDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("emailMembreDemandeur"), object.getEmailMembreDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=whatsappMembreDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("whatsappMembreDemandeur"), object.getWhatsappMembreDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=adresseMembreDemandeur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("adresseMembreDemandeur"), object.getAdresseMembreDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idSecteurDemandeur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdSecteurDemandeur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idSecteurDemandeur"), object.getIdSecteurDemandeur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idSecteurDemandeur"), object.getIdSecteurDemandeurMin(), object.getIdSecteurDemandeurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=secteurDemandeur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("secteurDemandeur"), object.getSecteurDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=descriptionSecteurDemandeur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("descriptionSecteurDemandeur"), object.getDescriptionSecteurDemandeur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idReceveur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdReceveur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idReceveur"), object.getIdReceveur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idReceveur"), object.getIdReceveurMin(), object.getIdReceveurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idSujetReceveur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdSujetReceveur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idSujetReceveur"), object.getIdSujetReceveur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idSujetReceveur"), object.getIdSujetReceveurMin(), object.getIdSujetReceveurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=libeleSujetReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("libeleSujetReceveur"), object.getLibeleSujetReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=descriptionSujetReceveur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("descriptionSujetReceveur"), object.getDescriptionSujetReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nomReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nomReceveur"), object.getNomReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=prenomReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("prenomReceveur"), object.getPrenomReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=fonctionReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("fonctionReceveur"), object.getFonctionReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=telephoneReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("telephoneReceveur"), object.getTelephoneReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=emailReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("emailReceveur"), object.getEmailReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=whatsappReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("whatsappReceveur"), object.getWhatsappReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=adresseReceveur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("adresseReceveur"), object.getAdresseReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idMembreReceveur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdMembreReceveur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idMembreReceveur"), object.getIdMembreReceveur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idMembreReceveur"), object.getIdMembreReceveurMin(), object.getIdMembreReceveurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=membreReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("membreReceveur"), object.getMembreReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=logoReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("logoReceveur"), object.getLogoReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=telephoneMembreReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("telephoneMembreReceveur"), object.getTelephoneMembreReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=emailMembreReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("emailMembreReceveur"), object.getEmailMembreReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=whatsappMembreReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("whatsappMembreReceveur"), object.getWhatsappMembreReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=adresseMembreReceveur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("adresseMembreReceveur"), object.getAdresseMembreReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idSecteurReceveur | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdSecteurReceveur()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idSecteurReceveur"), object.getIdSecteurReceveur()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idSecteurReceveur"), object.getIdSecteurReceveurMin(), object.getIdSecteurReceveurMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=secteurReceveur | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("secteurReceveur"), object.getSecteurReceveur(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=descriptionSecteurReceveur | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("descriptionSecteurReceveur"), object.getDescriptionSecteurReceveur(), predicates);
            
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idStatut | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdStatut()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idStatut"), object.getIdStatut()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("idStatut"), object.getIdStatutMin(), object.getIdStatutMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=statut | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("statut"), object.getStatut(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateChangementStatut | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateChangementStatut()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateChangementStatut"), object.getDateChangementStatut()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateChangementStatut"), object.getDateChangementStatutMin(), object.getDateChangementStatutMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=remarque | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("remarque"), object.getRemarque(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=historiqueStatuts | columnType=[jsonb] | isText=false | isNumeric=false");

            
            if(object.getHistoriqueStatuts()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        criteriaBuilder.function("jsonb", String.class, root.get("historiqueStatuts")),
						criteriaBuilder.function("jsonb", String.class, criteriaBuilder.literal(object.getHistoriqueStatuts()))
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=creneaux | columnType=[jsonb] | isText=false | isNumeric=false");

            
            if(object.getCreneaux()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        criteriaBuilder.function("jsonb", String.class, root.get("creneaux")),
						criteriaBuilder.function("jsonb", String.class, criteriaBuilder.literal(object.getCreneaux()))
                    )
                );
            }
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
