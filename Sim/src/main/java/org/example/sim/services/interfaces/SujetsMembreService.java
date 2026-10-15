package org.example.sim.services.interfaces;

import org.example.sim.models.SujetsMembre;
import org.example.sim.models.dto.SujetsMembreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface SujetsMembreService {
    Page<SujetsMembre> getAllSujetsMembre(Pageable pageable);

    Page<SujetsMembre> getAllSujetsMembre(Pageable pageable, SujetsMembreSearch object);

    SujetsMembre getSujetsMembreById(Integer id);

    public String exportSujetsMembreToCSV(List<SujetsMembre> sujetsMembre);

    

    SujetsMembre createSujetsMembre(SujetsMembre sujetsMembre);

    SujetsMembre updateSujetsMembre(Integer id, SujetsMembre sujetsMembre);

    void deleteSujetsMembre(Integer id);
    

}
