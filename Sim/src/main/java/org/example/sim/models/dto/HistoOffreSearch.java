package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Offre;

import org.example.sim.models.StatutOffre;



@Getter @Setter
public class HistoOffreSearch {

	private Integer id;

	private Offre idoffreOffres;
	
	private StatutOffre idstatutoffreStatutOffres;
	
	private java.time.LocalDateTime date;
	private java.time.LocalDateTime dateMin;
	private java.time.LocalDateTime dateMax;

	private String remarque;
	
}
