package org.example.sim.services.interfaces;

import org.example.sim.models.PlageHoraire;
import org.example.sim.models.dto.PlageHoraireSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface PlageHoraireService {
    Page<PlageHoraire> getAllPlageHoraire(Pageable pageable);

    Page<PlageHoraire> getAllPlageHoraire(Pageable pageable, PlageHoraireSearch object);

    PlageHoraire getPlageHoraireById(Integer id);

    public String exportPlageHoraireToCSV(List<PlageHoraire> plageHoraire);

    

    PlageHoraire createPlageHoraire(PlageHoraire plageHoraire);

    PlageHoraire updatePlageHoraire(Integer id, PlageHoraire plageHoraire);

    void deletePlageHoraire(Integer id);
    

}
