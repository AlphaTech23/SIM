package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="sujets")
public class Sujet  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name="libele", unique = false, nullable = false)
	@NotNull(message = "Libele cannot be null")
	@Size(max = 200)
	private String libele;
	@Column(name="description", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String description;
	@Column(name="nom", unique = false, nullable = false)
	@NotNull(message = "Nom cannot be null")
	@Size(max = 100)
	private String nom;
	@Column(name="prenom", unique = false, nullable = true)
	@Size(max = 100)
	private String prenom;
	@Column(name="fonction", unique = false, nullable = true)
	@Size(max = 150)
	private String fonction;
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
