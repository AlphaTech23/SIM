package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.Sujet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SujetRepository extends JpaRepository<Sujet, Integer>, JpaSpecificationExecutor<Sujet> {

}
