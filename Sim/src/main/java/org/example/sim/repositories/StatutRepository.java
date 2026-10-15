package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.Statut;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatutRepository extends JpaRepository<Statut, Integer>, JpaSpecificationExecutor<Statut> {

}
