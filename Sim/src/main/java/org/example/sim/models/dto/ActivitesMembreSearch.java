package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Membre;

import org.example.sim.models.Activite;



@Getter @Setter
public class ActivitesMembreSearch {

	private Integer id;

	private Membre idmembreMembres;
	
	private Activite idactiviteActivites;
	
	
}
