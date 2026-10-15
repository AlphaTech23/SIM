package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="statut_rendez_vous")
public class StatutRendezVou  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_rendez_vous")
	@NotNull(message = "IdRendezVous cannot be null")
	private RendezVou idrendezvousRendezVous;
	@ManyToOne
	@JoinColumn(name="id_statut")
	@NotNull(message = "IdStatut cannot be null")
	private Statut idstatutStatut;
	@Column(name="date", unique = false, nullable = false)
	@NotNull(message = "Date cannot be null")
	private java.time.LocalDateTime date;
	@Column(name="remarque", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String remarque;
	

}
