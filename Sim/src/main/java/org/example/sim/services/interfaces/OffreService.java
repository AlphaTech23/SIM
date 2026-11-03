package org.example.sim.services.interfaces;

import org.example.sim.models.Offre;
import org.example.sim.models.CiblesOffre;
import org.example.sim.models.HistoOffre;

import org.example.sim.models.dto.OffreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface OffreService {
    Page<Offre> getAllOffre(Pageable pageable);

    Page<Offre> getAllOffre(Pageable pageable, OffreSearch object);

    Offre getOffreById(Integer id);

    public String exportOffreToCSV(List<Offre> offre);

    

    Offre createOffre(Offre offre);

    Offre updateOffre(Integer id, Offre offre);

    void deleteOffre(Integer id);
    

    Offre createFullOffre(Offre offre,List<CiblesOffre> ciblesOffres,List<HistoOffre> histoOffres);
    Offre updateFullOffre(Integer id, Offre offre,List<CiblesOffre> ciblesOffres,List<HistoOffre> histoOffres);
    void deleteFullOffre(Integer id);
}
