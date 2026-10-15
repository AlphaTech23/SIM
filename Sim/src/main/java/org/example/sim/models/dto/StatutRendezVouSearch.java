package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.RendezVou;

import org.example.sim.models.Statut;



@Getter @Setter
public class StatutRendezVouSearch {

	private Integer id;

	private RendezVou idrendezvousRendezVous;
	
	private Statut idstatutStatut;
	
	private java.time.LocalDateTime date;
	private java.time.LocalDateTime dateMin;
	private java.time.LocalDateTime dateMax;

	private String remarque;
	
}
