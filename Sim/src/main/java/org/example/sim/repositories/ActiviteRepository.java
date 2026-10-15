package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.Activite;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActiviteRepository extends JpaRepository<Activite, Integer>, JpaSpecificationExecutor<Activite> {

}
