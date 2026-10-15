package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="plage_horaire")
public class PlageHoraire  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_rendez_vous")
	@NotNull(message = "IdRendezVous cannot be null")
	private RendezVou idrendezvousRendezVous;
	@Column(name="jour_debut", unique = false, nullable = false)
	@NotNull(message = "JourDebut cannot be null")
	private java.time.LocalDateTime jourDebut;
	@Column(name="jour_fin", unique = false, nullable = false)
	@NotNull(message = "JourFin cannot be null")
	private java.time.LocalDateTime jourFin;
	

}
