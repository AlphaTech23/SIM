package org.example.sim.services.interfaces;

import org.example.sim.models.VRendezVousRefuse;
import org.example.sim.models.dto.VRendezVousRefuseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VRendezVousRefuseService {
    Page<VRendezVousRefuse> getAllVRendezVousRefuse(Pageable pageable);

    Page<VRendezVousRefuse> getAllVRendezVousRefuse(Pageable pageable, VRendezVousRefuseSearch object);

    VRendezVousRefuse getVRendezVousRefuseById(Integer idRendezVous);

    public String exportVRendezVousRefuseToCSV(List<VRendezVousRefuse> vRendezVousRefuse);


}
