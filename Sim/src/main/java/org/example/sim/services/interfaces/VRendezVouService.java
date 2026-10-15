package org.example.sim.services.interfaces;

import org.example.sim.models.VRendezVou;
import org.example.sim.models.dto.VRendezVouSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VRendezVouService {
    Page<VRendezVou> getAllVRendezVou(Pageable pageable);

    Page<VRendezVou> getAllVRendezVou(Pageable pageable, VRendezVouSearch object);

    VRendezVou getVRendezVouById(Integer idRendezVous);

    public String exportVRendezVouToCSV(List<VRendezVou> vRendezVou);


}
