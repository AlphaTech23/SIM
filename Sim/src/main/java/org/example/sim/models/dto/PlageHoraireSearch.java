package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.RendezVou;



@Getter @Setter
public class PlageHoraireSearch {

	private Integer id;

	private RendezVou idrendezvousRendezVous;
	
	private java.time.LocalDateTime jourDebut;
	private java.time.LocalDateTime jourDebutMin;
	private java.time.LocalDateTime jourDebutMax;

	private java.time.LocalDateTime jourFin;
	private java.time.LocalDateTime jourFinMin;
	private java.time.LocalDateTime jourFinMax;

	
}
