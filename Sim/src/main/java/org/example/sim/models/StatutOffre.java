package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="statut_offres")
public class StatutOffre  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name="libele", unique = true, nullable = false)
	@NotNull(message = "Libele cannot be null")
	@Size(max = 100)
	private String libele;
	

}
