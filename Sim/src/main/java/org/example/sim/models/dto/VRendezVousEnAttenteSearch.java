package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VRendezVousEnAttenteSearch {

	private Integer idRendezVous;

	private Integer idDemandeur;
	private Integer idDemandeurMin;
	private Integer idDemandeurMax;

	private Integer idSujetDemandeur;
	private Integer idSujetDemandeurMin;
	private Integer idSujetDemandeurMax;

	private String libeleSujetDemandeur;
	private String descriptionSujetDemandeur;
	private String nomDemandeur;
	private String prenomDemandeur;
	private String fonctionDemandeur;
	private String telephoneDemandeur;
	private String emailDemandeur;
	private String whatsappDemandeur;
	private String adresseDemandeur;
	private Integer idMembreDemandeur;
	private Integer idMembreDemandeurMin;
	private Integer idMembreDemandeurMax;

	private String membreDemandeur;
	private String logoDemandeur;
	private String telephoneMembreDemandeur;
	private String emailMembreDemandeur;
	private String whatsappMembreDemandeur;
	private String adresseMembreDemandeur;
	private Integer idSecteurDemandeur;
	private Integer idSecteurDemandeurMin;
	private Integer idSecteurDemandeurMax;

	private String secteurDemandeur;
	private String descriptionSecteurDemandeur;
	private Integer idReceveur;
	private Integer idReceveurMin;
	private Integer idReceveurMax;

	private Integer idSujetReceveur;
	private Integer idSujetReceveurMin;
	private Integer idSujetReceveurMax;

	private String libeleSujetReceveur;
	private String descriptionSujetReceveur;
	private String nomReceveur;
	private String prenomReceveur;
	private String fonctionReceveur;
	private String telephoneReceveur;
	private String emailReceveur;
	private String whatsappReceveur;
	private String adresseReceveur;
	private Integer idMembreReceveur;
	private Integer idMembreReceveurMin;
	private Integer idMembreReceveurMax;

	private String membreReceveur;
	private String logoReceveur;
	private String telephoneMembreReceveur;
	private String emailMembreReceveur;
	private String whatsappMembreReceveur;
	private String adresseMembreReceveur;
	private Integer idSecteurReceveur;
	private Integer idSecteurReceveurMin;
	private Integer idSecteurReceveurMax;

	private String secteurReceveur;
	private String descriptionSecteurReceveur;
	private String contexte;
	private java.time.LocalDateTime dateRdv;
	private java.time.LocalDateTime dateRdvMin;
	private java.time.LocalDateTime dateRdvMax;

	private Integer idStatut;
	private Integer idStatutMin;
	private Integer idStatutMax;

	private String statut;
	private java.time.LocalDateTime dateChangementStatut;
	private java.time.LocalDateTime dateChangementStatutMin;
	private java.time.LocalDateTime dateChangementStatutMax;

	private String remarque;
	private String historiqueStatuts;
	private String creneaux;
	
}
