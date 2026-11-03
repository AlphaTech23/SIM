package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="histo_offres")
public class HistoOffre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_offre")
	@NotNull(message = "IdOffre cannot be null")
	private Offre idoffreOffres;
	@ManyToOne
	@JoinColumn(name="id_statut_offre")
	@NotNull(message = "IdStatutOffre cannot be null")
	private StatutOffre idstatutoffreStatutOffres;
	@Column(name="date", unique = false, nullable = false)
	@NotNull(message = "Date cannot be null")
	private java.time.LocalDateTime date;
	@Column(name="remarque", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String remarque;
	

}
