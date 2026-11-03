package org.example.sim.services.interfaces;

import org.example.sim.models.CiblesOffre;
import org.example.sim.models.dto.CiblesOffreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface CiblesOffreService {
    Page<CiblesOffre> getAllCiblesOffre(Pageable pageable);

    Page<CiblesOffre> getAllCiblesOffre(Pageable pageable, CiblesOffreSearch object);

    CiblesOffre getCiblesOffreById(Integer id);

    public String exportCiblesOffreToCSV(List<CiblesOffre> ciblesOffre);

    

    CiblesOffre createCiblesOffre(CiblesOffre ciblesOffre);

    CiblesOffre updateCiblesOffre(Integer id, CiblesOffre ciblesOffre);

    void deleteCiblesOffre(Integer id);
    

}
