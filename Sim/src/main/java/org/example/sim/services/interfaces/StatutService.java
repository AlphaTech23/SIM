package org.example.sim.services.interfaces;

import org.example.sim.models.Statut;
import org.example.sim.models.StatutRendezVou;

import org.example.sim.models.dto.StatutSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface StatutService {
    Page<Statut> getAllStatut(Pageable pageable);

    Page<Statut> getAllStatut(Pageable pageable, StatutSearch object);

    Statut getStatutById(Integer id);

    public String exportStatutToCSV(List<Statut> statut);

    

    Statut createStatut(Statut statut);

    Statut updateStatut(Integer id, Statut statut);

    void deleteStatut(Integer id);
    

    Statut createFullStatut(Statut statut,List<StatutRendezVou> statutRendezVous);
    Statut updateFullStatut(Integer id, Statut statut,List<StatutRendezVou> statutRendezVous);
    void deleteFullStatut(Integer id);
}
