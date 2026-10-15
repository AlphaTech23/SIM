package org.example.sim.services.interfaces;

import org.example.sim.models.Activite;
import org.example.sim.models.ActivitesMembre;

import org.example.sim.models.dto.ActiviteSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface ActiviteService {
    Page<Activite> getAllActivite(Pageable pageable);

    Page<Activite> getAllActivite(Pageable pageable, ActiviteSearch object);

    Activite getActiviteById(Integer id);

    public String exportActiviteToCSV(List<Activite> activite);

    

    Activite createActivite(Activite activite);

    Activite updateActivite(Integer id, Activite activite);

    void deleteActivite(Integer id);
    

    Activite createFullActivite(Activite activite,List<ActivitesMembre> activitesMembres);
    Activite updateFullActivite(Integer id, Activite activite,List<ActivitesMembre> activitesMembres);
    void deleteFullActivite(Integer id);
}
