package org.example.sim.services.interfaces;

import org.example.sim.models.VRendezVousAnnule;
import org.example.sim.models.dto.VRendezVousAnnuleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VRendezVousAnnuleService {
    Page<VRendezVousAnnule> getAllVRendezVousAnnule(Pageable pageable);

    Page<VRendezVousAnnule> getAllVRendezVousAnnule(Pageable pageable, VRendezVousAnnuleSearch object);

    VRendezVousAnnule getVRendezVousAnnuleById(Integer idRendezVous);

    public String exportVRendezVousAnnuleToCSV(List<VRendezVousAnnule> vRendezVousAnnule);


}
