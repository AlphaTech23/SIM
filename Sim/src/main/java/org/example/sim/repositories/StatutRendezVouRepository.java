package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.StatutRendezVou;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Statut;



public interface StatutRendezVouRepository extends JpaRepository<StatutRendezVou, Integer>, JpaSpecificationExecutor<StatutRendezVou> {

List<StatutRendezVou> findByIdstatutStatut(Statut idstatutStatut);



}
