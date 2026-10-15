package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="rendez_vous")
public class RendezVou  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_demandeur")
	@NotNull(message = "IdDemandeur cannot be null")
	private Membre iddemandeurMembres;
	@ManyToOne
	@JoinColumn(name="id_receveur")
	@NotNull(message = "IdReceveur cannot be null")
	private SujetsMembre idreceveurSujetsMembres;
	@Column(name="contexte", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String contexte;
	@Column(name="date_rdv", unique = false, nullable = true)
	private java.time.LocalDateTime dateRdv;
	

}
