package org.example.sim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="activites")
public class Activite  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name="libele", unique = true, nullable = false)
	@NotNull(message = "Libele cannot be null")
	@Size(max = 150)
	private String libele;
	@Column(name="description", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String description;
	

}
