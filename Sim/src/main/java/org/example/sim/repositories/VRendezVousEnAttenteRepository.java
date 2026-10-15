package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.VRendezVousEnAttente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VRendezVousEnAttenteRepository extends JpaRepository<VRendezVousEnAttente, Integer>, JpaSpecificationExecutor<VRendezVousEnAttente> {

}
