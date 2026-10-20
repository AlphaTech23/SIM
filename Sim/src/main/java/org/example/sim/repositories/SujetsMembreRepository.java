package org.example.sim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sim.models.SujetsMembre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import org.example.sim.models.Membre;
import org.example.sim.models.Sujet;



public interface SujetsMembreRepository extends JpaRepository<SujetsMembre, Integer>, JpaSpecificationExecutor<SujetsMembre> {

List<SujetsMembre> findByIdmembreMembres(Membre idmembreMembres);
List<SujetsMembre> findByIdsujetSujets(Sujet idsujetSujets);

SujetsMembre findByIdmembreMembresAndIdsujetSujets(Membre idmembreMembres, Sujet idsujetSujets);

}
