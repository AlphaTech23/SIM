package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.VRendezVousRefuse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VRendezVousRefuseRepository extends JpaRepository<VRendezVousRefuse, Integer>, JpaSpecificationExecutor<VRendezVousRefuse> {

}
