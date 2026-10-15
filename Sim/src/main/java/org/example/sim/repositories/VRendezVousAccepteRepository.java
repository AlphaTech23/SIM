package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.VRendezVousAccepte;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VRendezVousAccepteRepository extends JpaRepository<VRendezVousAccepte, Integer>, JpaSpecificationExecutor<VRendezVousAccepte> {

}
