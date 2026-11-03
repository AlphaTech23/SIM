package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="offres")
public class Offre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_type_offre")
	@NotNull(message = "IdTypeOffre cannot be null")
	private TypeOffre idtypeoffreTypeOffres;
	@ManyToOne
	@JoinColumn(name="id_membre")
	@NotNull(message = "IdMembre cannot be null")
	private Membre idmembreMembres;
	@Column(name="titre_offre", unique = false, nullable = false)
	@NotNull(message = "TitreOffre cannot be null")
	@Size(max = 255)
	private String titreOffre;
	@Column(name="descriptions", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String descriptions;
	@Column(name="conditions", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String conditions;
	@Column(name="date_creation", unique = false, nullable = false)
	@NotNull(message = "DateCreation cannot be null")
	private java.time.LocalDateTime dateCreation;
	@Column(name="date_fin_validite", unique = false, nullable = true)
	private java.time.LocalDate dateFinValidite;
	

}
