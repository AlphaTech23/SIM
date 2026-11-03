package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.RendezVou;



@Getter @Setter
public class ChoixHoraireDemandeurSearch {

	private Integer id;

	private RendezVou idrendezvousRendezVous;
	
	private java.time.LocalDateTime dateChoix;
	private java.time.LocalDateTime dateChoixMin;
	private java.time.LocalDateTime dateChoixMax;

	
}
