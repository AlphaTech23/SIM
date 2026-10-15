package org.example.sim.services.interfaces;

import org.example.sim.models.Secteur;
import org.example.sim.models.Membre;

import org.example.sim.models.dto.SecteurSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface SecteurService {
    Page<Secteur> getAllSecteur(Pageable pageable);

    Page<Secteur> getAllSecteur(Pageable pageable, SecteurSearch object);

    Secteur getSecteurById(Integer id);

    public String exportSecteurToCSV(List<Secteur> secteur);

    

    Secteur createSecteur(Secteur secteur);

    Secteur updateSecteur(Integer id, Secteur secteur);

    void deleteSecteur(Integer id);
    

    Secteur createFullSecteur(Secteur secteur,List<Membre> membres);
    Secteur updateFullSecteur(Integer id, Secteur secteur,List<Membre> membres);
    void deleteFullSecteur(Integer id);
}
