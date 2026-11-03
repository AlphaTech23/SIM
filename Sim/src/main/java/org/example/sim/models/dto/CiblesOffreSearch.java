package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Offre;

import org.example.sim.models.Membre;



@Getter @Setter
public class CiblesOffreSearch {

	private Integer id;

	private Offre idoffreOffres;
	
	private Membre idmembreMembres;
	
	private java.time.LocalDateTime dateEnvoi;
	private java.time.LocalDateTime dateEnvoiMin;
	private java.time.LocalDateTime dateEnvoiMax;

	
}
