package org.example.sim.services.interfaces;

import org.example.sim.models.VRendezVousAccepte;
import org.example.sim.models.dto.VRendezVousAccepteSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VRendezVousAccepteService {
    Page<VRendezVousAccepte> getAllVRendezVousAccepte(Pageable pageable);

    Page<VRendezVousAccepte> getAllVRendezVousAccepte(Pageable pageable, VRendezVousAccepteSearch object);

    VRendezVousAccepte getVRendezVousAccepteById(Integer idRendezVous);

    public String exportVRendezVousAccepteToCSV(List<VRendezVousAccepte> vRendezVousAccepte);


}
