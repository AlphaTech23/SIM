package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.Offre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Membre;



public interface OffreRepository extends JpaRepository<Offre, Integer>, JpaSpecificationExecutor<Offre> {

List<Offre> findByIdmembreMembres(Membre idmembreMembres);



}
