package org.example.sim.services.interfaces;

import org.example.sim.models.VRendezVousEnAttente;
import org.example.sim.models.dto.VRendezVousEnAttenteSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VRendezVousEnAttenteService {
    Page<VRendezVousEnAttente> getAllVRendezVousEnAttente(Pageable pageable);

    Page<VRendezVousEnAttente> getAllVRendezVousEnAttente(Pageable pageable, VRendezVousEnAttenteSearch object);

    VRendezVousEnAttente getVRendezVousEnAttenteById(Integer idRendezVous);

    public String exportVRendezVousEnAttenteToCSV(List<VRendezVousEnAttente> vRendezVousEnAttente);


}
