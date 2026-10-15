package org.example.sim.services.interfaces;

import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.dto.ActivitesMembreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface ActivitesMembreService {
    Page<ActivitesMembre> getAllActivitesMembre(Pageable pageable);

    Page<ActivitesMembre> getAllActivitesMembre(Pageable pageable, ActivitesMembreSearch object);

    ActivitesMembre getActivitesMembreById(Integer id);

    public String exportActivitesMembreToCSV(List<ActivitesMembre> activitesMembre);

    

    ActivitesMembre createActivitesMembre(ActivitesMembre activitesMembre);

    ActivitesMembre updateActivitesMembre(Integer id, ActivitesMembre activitesMembre);

    void deleteActivitesMembre(Integer id);
    

}
