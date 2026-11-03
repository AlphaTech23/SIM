package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.HistoOffre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Offre;



public interface HistoOffreRepository extends JpaRepository<HistoOffre, Integer>, JpaSpecificationExecutor<HistoOffre> {

List<HistoOffre> findByIdoffreOffres(Offre idoffreOffres);



}
