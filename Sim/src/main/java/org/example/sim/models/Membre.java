package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="membres")
public class Membre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_secteur")
	@NotNull(message = "IdSecteur cannot be null")
	private Secteur idsecteurSecteurs;
	@Column(name="intitule", unique = false, nullable = false)
	@NotNull(message = "Intitule cannot be null")
	@Size(max = 200)
	private String intitule;
	@Column(name="logo", unique = false, nullable = true)
	@Size(max = 255)
	private String logo;
	@Column(name="telephone", unique = false, nullable = true)
	@Size(max = 30)
	private String telephone;
	@Column(name="email", unique = false, nullable = true)
	@Size(max = 150)
	private String email;
	@Column(name="whatsapp", unique = false, nullable = true)
	@Size(max = 30)
	private String whatsapp;
	@Column(name="adresse", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String adresse;
	

}
