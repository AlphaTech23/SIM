package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.StatutOffre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatutOffreRepository extends JpaRepository<StatutOffre, Integer>, JpaSpecificationExecutor<StatutOffre> {

}
