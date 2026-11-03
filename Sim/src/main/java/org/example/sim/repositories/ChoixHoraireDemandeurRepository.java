package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.ChoixHoraireDemandeur;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChoixHoraireDemandeurRepository extends JpaRepository<ChoixHoraireDemandeur, Integer>, JpaSpecificationExecutor<ChoixHoraireDemandeur> {

}
