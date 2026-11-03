package org.example.sim.services.interfaces;

import org.example.sim.models.StatutOffre;
import org.example.sim.models.dto.StatutOffreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface StatutOffreService {
    Page<StatutOffre> getAllStatutOffre(Pageable pageable);

    Page<StatutOffre> getAllStatutOffre(Pageable pageable, StatutOffreSearch object);

    StatutOffre getStatutOffreById(Integer id);

    public String exportStatutOffreToCSV(List<StatutOffre> statutOffre);

    

    StatutOffre createStatutOffre(StatutOffre statutOffre);

    StatutOffre updateStatutOffre(Integer id, StatutOffre statutOffre);

    void deleteStatutOffre(Integer id);
    

}
