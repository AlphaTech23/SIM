package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.Membre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Secteur;



public interface MembreRepository extends JpaRepository<Membre, Integer>, JpaSpecificationExecutor<Membre> {

List<Membre> findByIdsecteurSecteurs(Secteur idsecteurSecteurs);



}
