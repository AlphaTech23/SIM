package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.VRendezVousAnnule;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VRendezVousAnnuleRepository extends JpaRepository<VRendezVousAnnule, Integer>, JpaSpecificationExecutor<VRendezVousAnnule> {

}
