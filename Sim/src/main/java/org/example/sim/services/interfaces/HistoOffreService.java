package org.example.sim.services.interfaces;

import org.example.sim.models.HistoOffre;
import org.example.sim.models.dto.HistoOffreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface HistoOffreService {
    Page<HistoOffre> getAllHistoOffre(Pageable pageable);

    Page<HistoOffre> getAllHistoOffre(Pageable pageable, HistoOffreSearch object);

    HistoOffre getHistoOffreById(Integer id);

    public String exportHistoOffreToCSV(List<HistoOffre> histoOffre);

    

    HistoOffre createHistoOffre(HistoOffre histoOffre);

    HistoOffre updateHistoOffre(Integer id, HistoOffre histoOffre);

    void deleteHistoOffre(Integer id);
    

}
