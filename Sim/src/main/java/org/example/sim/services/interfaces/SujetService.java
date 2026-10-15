package org.example.sim.services.interfaces;

import org.example.sim.models.Sujet;
import org.example.sim.models.SujetsMembre;

import org.example.sim.models.dto.SujetSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface SujetService {
    Page<Sujet> getAllSujet(Pageable pageable);

    Page<Sujet> getAllSujet(Pageable pageable, SujetSearch object);

    Sujet getSujetById(Integer id);

    public String exportSujetToCSV(List<Sujet> sujet);

    

    Sujet createSujet(Sujet sujet);

    Sujet updateSujet(Integer id, Sujet sujet);

    void deleteSujet(Integer id);
    

    Sujet createFullSujet(Sujet sujet,List<SujetsMembre> sujetsMembres);
    Sujet updateFullSujet(Integer id, Sujet sujet,List<SujetsMembre> sujetsMembres);
    void deleteFullSujet(Integer id);
}
