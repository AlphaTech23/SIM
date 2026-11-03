package org.example.sim.services.interfaces;

import org.example.sim.models.ChoixHoraireDemandeur;
import org.example.sim.models.dto.ChoixHoraireDemandeurSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface ChoixHoraireDemandeurService {
    Page<ChoixHoraireDemandeur> getAllChoixHoraireDemandeur(Pageable pageable);

    Page<ChoixHoraireDemandeur> getAllChoixHoraireDemandeur(Pageable pageable, ChoixHoraireDemandeurSearch object);

    ChoixHoraireDemandeur getChoixHoraireDemandeurById(Integer id);

    public String exportChoixHoraireDemandeurToCSV(List<ChoixHoraireDemandeur> choixHoraireDemandeur);

    

    ChoixHoraireDemandeur createChoixHoraireDemandeur(ChoixHoraireDemandeur choixHoraireDemandeur);

    ChoixHoraireDemandeur updateChoixHoraireDemandeur(Integer id, ChoixHoraireDemandeur choixHoraireDemandeur);

    void deleteChoixHoraireDemandeur(Integer id);
    

}
