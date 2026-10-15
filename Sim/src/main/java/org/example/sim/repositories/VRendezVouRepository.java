package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.VRendezVou;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VRendezVouRepository extends JpaRepository<VRendezVou, Integer>, JpaSpecificationExecutor<VRendezVou> {

}
