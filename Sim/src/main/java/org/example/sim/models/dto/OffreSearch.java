package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.TypeOffre;

import org.example.sim.models.Membre;



@Getter @Setter
public class OffreSearch {

	private Integer id;

	private TypeOffre idtypeoffreTypeOffres;
	
	private Membre idmembreMembres;
	
	private String titreOffre;
	private String descriptions;
	private String conditions;
	private java.time.LocalDateTime dateCreation;
	private java.time.LocalDateTime dateCreationMin;
	private java.time.LocalDateTime dateCreationMax;

	private java.time.LocalDate dateFinValidite;
	private java.time.LocalDate dateFinValiditeMin;
	private java.time.LocalDate dateFinValiditeMax;

	
}
