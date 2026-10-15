package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.PlageHoraire;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlageHoraireRepository extends JpaRepository<PlageHoraire, Integer>, JpaSpecificationExecutor<PlageHoraire> {

}
