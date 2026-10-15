package org.example.sim.services.interfaces;

import org.example.sim.models.RendezVou;
import org.example.sim.models.dto.RendezVouSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface RendezVouService {
    Page<RendezVou> getAllRendezVou(Pageable pageable);

    Page<RendezVou> getAllRendezVou(Pageable pageable, RendezVouSearch object);

    RendezVou getRendezVouById(Integer id);

    public String exportRendezVouToCSV(List<RendezVou> rendezVou);

    

    RendezVou createRendezVou(RendezVou rendezVou);

    RendezVou updateRendezVou(Integer id, RendezVou rendezVou);

    void deleteRendezVou(Integer id);
    

}
