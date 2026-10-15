package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Membre;

import org.example.sim.models.Sujet;



@Getter @Setter
public class SujetsMembreSearch {

	private Integer id;

	private Membre idmembreMembres;
	
	private Sujet idsujetSujets;
	
	
}
