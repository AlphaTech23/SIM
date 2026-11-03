package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="cibles_offres")
public class CiblesOffre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_offre")
	@NotNull(message = "IdOffre cannot be null")
	private Offre idoffreOffres;
	@ManyToOne
	@JoinColumn(name="id_membre")
	@NotNull(message = "IdMembre cannot be null")
	private Membre idmembreMembres;
	@Column(name="date_envoi", unique = false, nullable = false)
	@NotNull(message = "DateEnvoi cannot be null")
	private java.time.LocalDateTime dateEnvoi;
	

}
