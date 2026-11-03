package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="choix_horaire_demandeur")
public class ChoixHoraireDemandeur  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_rendez_vous")
	@NotNull(message = "IdRendezVous cannot be null")
	private RendezVou idrendezvousRendezVous;
	@Column(name="date_choix", unique = false, nullable = false)
	@NotNull(message = "DateChoix cannot be null")
	private java.time.LocalDateTime dateChoix;
	

}
