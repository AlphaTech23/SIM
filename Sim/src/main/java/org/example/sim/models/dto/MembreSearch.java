package org.example.sim.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sim.models.Secteur;



@Getter @Setter
public class MembreSearch {

	private Integer id;

	private Secteur idsecteurSecteurs;
	
	private String intitule;
	private String logo;
	private String telephone;
	private String email;
	private String whatsapp;
	private String adresse;
	
}
