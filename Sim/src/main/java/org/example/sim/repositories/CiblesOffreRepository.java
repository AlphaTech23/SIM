package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.CiblesOffre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Offre;
import org.example.sim.models.Membre;



public interface CiblesOffreRepository extends JpaRepository<CiblesOffre, Integer>, JpaSpecificationExecutor<CiblesOffre> {

List<CiblesOffre> findByIdoffreOffres(Offre idoffreOffres);
List<CiblesOffre> findByIdmembreMembres(Membre idmembreMembres);



}
