package org.example.sim.services.interfaces;

import org.example.sim.models.Membre;
import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.RendezVou;
import org.example.sim.models.SujetsMembre;

import org.example.sim.models.dto.MembreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface MembreService {
    Page<Membre> getAllMembre(Pageable pageable);

    Page<Membre> getAllMembre(Pageable pageable, MembreSearch object);

    Membre getMembreById(Integer id);

    public String exportMembreToCSV(List<Membre> membre);

    

    Membre createMembre(Membre membre);

    Membre updateMembre(Integer id, Membre membre);

    void deleteMembre(Integer id);
    

    Membre createFullMembre(Membre membre,List<ActivitesMembre> activitesMembres,List<RendezVou> rendezVous,List<SujetsMembre> sujetsMembres);
    Membre updateFullMembre(Integer id, Membre membre,List<ActivitesMembre> activitesMembres,List<RendezVou> rendezVous,List<SujetsMembre> sujetsMembres);
    void deleteFullMembre(Integer id);
}
