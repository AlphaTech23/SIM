package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.ActivitesMembre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Membre;
import org.example.sim.models.Activite;



public interface ActivitesMembreRepository extends JpaRepository<ActivitesMembre, Integer>, JpaSpecificationExecutor<ActivitesMembre> {

List<ActivitesMembre> findByIdmembreMembres(Membre idmembreMembres);
List<ActivitesMembre> findByIdactiviteActivites(Activite idactiviteActivites);



}
