package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="activites_membres")
public class ActivitesMembre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_membre")
	@NotNull(message = "IdMembre cannot be null")
	private Membre idmembreMembres;
	@ManyToOne
	@JoinColumn(name="id_activite")
	@NotNull(message = "IdActivite cannot be null")
	private Activite idactiviteActivites;
	

}
