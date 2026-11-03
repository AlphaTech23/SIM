package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.TypeOffre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeOffreRepository extends JpaRepository<TypeOffre, Integer>, JpaSpecificationExecutor<TypeOffre> {

}
