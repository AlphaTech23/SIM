package org.example.sim.services.interfaces;

import org.example.sim.models.StatutRendezVou;
import org.example.sim.models.dto.StatutRendezVouSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface StatutRendezVouService {
    Page<StatutRendezVou> getAllStatutRendezVou(Pageable pageable);

    Page<StatutRendezVou> getAllStatutRendezVou(Pageable pageable, StatutRendezVouSearch object);

    StatutRendezVou getStatutRendezVouById(Integer id);

    public String exportStatutRendezVouToCSV(List<StatutRendezVou> statutRendezVou);

    

    StatutRendezVou createStatutRendezVou(StatutRendezVou statutRendezVou);

    StatutRendezVou updateStatutRendezVou(Integer id, StatutRendezVou statutRendezVou);

    void deleteStatutRendezVou(Integer id);
    

}
