package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.RendezVou;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Membre;



public interface RendezVouRepository extends JpaRepository<RendezVou, Integer>, JpaSpecificationExecutor<RendezVou> {

List<RendezVou> findByIddemandeurMembres(Membre iddemandeurMembres);



}
