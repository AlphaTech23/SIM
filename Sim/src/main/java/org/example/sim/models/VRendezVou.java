package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Setter
@Getter
@Entity
@Immutable
@Table(name="v_rendez_vous")
public class VRendezVou  {
	@Id
	@Column(name="id_rendez_vous", unique = false, nullable = true)
	private Integer idRendezVous;
	@Column(name="id_demandeur", unique = false, nullable = true)
	private Integer idDemandeur;
	@Column(name="id_sujet_demandeur", unique = false, nullable = true)
	private Integer idSujetDemandeur;
	@Column(name="libele_sujet_demandeur", unique = false, nullable = true)
	@Size(max = 200)
	private String libeleSujetDemandeur;
	@Column(name="description_sujet_demandeur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String descriptionSujetDemandeur;
	@Column(name="nom_demandeur", unique = false, nullable = true)
	@Size(max = 100)
	private String nomDemandeur;
	@Column(name="prenom_demandeur", unique = false, nullable = true)
	@Size(max = 100)
	private String prenomDemandeur;
	@Column(name="fonction_demandeur", unique = false, nullable = true)
	@Size(max = 150)
	private String fonctionDemandeur;
	@Column(name="telephone_demandeur", unique = false, nullable = true)
	@Size(max = 30)
	private String telephoneDemandeur;
	@Column(name="email_demandeur", unique = false, nullable = true)
	@Size(max = 150)
	private String emailDemandeur;
	@Column(name="whatsapp_demandeur", unique = false, nullable = true)
	@Size(max = 30)
	private String whatsappDemandeur;
	@Column(name="adresse_demandeur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String adresseDemandeur;
	@Column(name="id_membre_demandeur", unique = false, nullable = true)
	private Integer idMembreDemandeur;
	@Column(name="membre_demandeur", unique = false, nullable = true)
	@Size(max = 200)
	private String membreDemandeur;
	@Column(name="logo_demandeur", unique = false, nullable = true)
	@Size(max = 255)
	private String logoDemandeur;
	@Column(name="telephone_membre_demandeur", unique = false, nullable = true)
	@Size(max = 30)
	private String telephoneMembreDemandeur;
	@Column(name="email_membre_demandeur", unique = false, nullable = true)
	@Size(max = 150)
	private String emailMembreDemandeur;
	@Column(name="whatsapp_membre_demandeur", unique = false, nullable = true)
	@Size(max = 30)
	private String whatsappMembreDemandeur;
	@Column(name="adresse_membre_demandeur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String adresseMembreDemandeur;
	@Column(name="id_secteur_demandeur", unique = false, nullable = true)
	private Integer idSecteurDemandeur;
	@Column(name="secteur_demandeur", unique = false, nullable = true)
	@Size(max = 150)
	private String secteurDemandeur;
	@Column(name="description_secteur_demandeur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String descriptionSecteurDemandeur;
	@Column(name="id_receveur", unique = false, nullable = true)
	private Integer idReceveur;
	@Column(name="id_sujet_receveur", unique = false, nullable = true)
	private Integer idSujetReceveur;
	@Column(name="libele_sujet_receveur", unique = false, nullable = true)
	@Size(max = 200)
	private String libeleSujetReceveur;
	@Column(name="description_sujet_receveur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String descriptionSujetReceveur;
	@Column(name="nom_receveur", unique = false, nullable = true)
	@Size(max = 100)
	private String nomReceveur;
	@Column(name="prenom_receveur", unique = false, nullable = true)
	@Size(max = 100)
	private String prenomReceveur;
	@Column(name="fonction_receveur", unique = false, nullable = true)
	@Size(max = 150)
	private String fonctionReceveur;
	@Column(name="telephone_receveur", unique = false, nullable = true)
	@Size(max = 30)
	private String telephoneReceveur;
	@Column(name="email_receveur", unique = false, nullable = true)
	@Size(max = 150)
	private String emailReceveur;
	@Column(name="whatsapp_receveur", unique = false, nullable = true)
	@Size(max = 30)
	private String whatsappReceveur;
	@Column(name="adresse_receveur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String adresseReceveur;
	@Column(name="id_membre_receveur", unique = false, nullable = true)
	private Integer idMembreReceveur;
	@Column(name="membre_receveur", unique = false, nullable = true)
	@Size(max = 200)
	private String membreReceveur;
	@Column(name="logo_receveur", unique = false, nullable = true)
	@Size(max = 255)
	private String logoReceveur;
	@Column(name="telephone_membre_receveur", unique = false, nullable = true)
	@Size(max = 30)
	private String telephoneMembreReceveur;
	@Column(name="email_membre_receveur", unique = false, nullable = true)
	@Size(max = 150)
	private String emailMembreReceveur;
	@Column(name="whatsapp_membre_receveur", unique = false, nullable = true)
	@Size(max = 30)
	private String whatsappMembreReceveur;
	@Column(name="adresse_membre_receveur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String adresseMembreReceveur;
	@Column(name="id_secteur_receveur", unique = false, nullable = true)
	private Integer idSecteurReceveur;
	@Column(name="secteur_receveur", unique = false, nullable = true)
	@Size(max = 150)
	private String secteurReceveur;
	@Column(name="description_secteur_receveur", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String descriptionSecteurReceveur;
	@Column(name="contexte", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String contexte;
	@Column(name="date_rdv", unique = false, nullable = true)
	private java.time.LocalDateTime dateRdv;
	@Column(name="id_statut", unique = false, nullable = true)
	private Integer idStatut;
	@Column(name="statut", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String statut;
	@Column(name="date_changement_statut", unique = false, nullable = true)
	private java.time.LocalDateTime dateChangementStatut;
	@Column(name="remarque", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String remarque;
	@Column(name="historique_statuts", unique = false, nullable = true)
	@org.hibernate.annotations.ColumnTransformer(read = "CAST(historique_statuts as varchar)", write = "CAST(? as jsonb)")
	private String historiqueStatuts;
	@Column(name="creneaux", unique = false, nullable = true)
	@org.hibernate.annotations.ColumnTransformer(read = "CAST(creneaux as varchar)", write = "CAST(? as jsonb)")
	private String creneaux;
	

}
