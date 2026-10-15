package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Membre;

import org.example.sim.models.SujetsMembre;



@Getter @Setter
public class RendezVouSearch {

	private Integer id;

	private Membre iddemandeurMembres;
	
	private SujetsMembre idreceveurSujetsMembres;
	
	private String contexte;
	private java.time.LocalDateTime dateRdv;
	private java.time.LocalDateTime dateRdvMin;
	private java.time.LocalDateTime dateRdvMax;

	
}
