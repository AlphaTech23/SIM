package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.VRendezVousAnnule;
import org.example.sim.models.dto.VRendezVousAnnuleSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.VRendezVousAnnuleRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.VRendezVousAnnuleService;
import org.example.sim.specification.VRendezVousAnnuleSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVRendezVousAnnuleService implements VRendezVousAnnuleService {
	private final VRendezVousAnnuleRepository vRendezVousAnnuleRepository;

	public DefaultVRendezVousAnnuleService(VRendezVousAnnuleRepository vRendezVousAnnuleRepository) {
	   this.vRendezVousAnnuleRepository = vRendezVousAnnuleRepository;
	}

	@Override
	public String exportVRendezVousAnnuleToCSV(List<VRendezVousAnnule> vRendezVousAnnule) {
	   return ExportUtils.generateCsv(vRendezVousAnnule);
	}  

	@Override
	public Page<VRendezVousAnnule> getAllVRendezVousAnnule(Pageable pageable) {
	    try {
	        return vRendezVousAnnuleRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous annule", ex);
	    }
	}

	@Override
	public Page<VRendezVousAnnule> getAllVRendezVousAnnule(Pageable pageable, VRendezVousAnnuleSearch object) {
	    try {
	        Specification<VRendezVousAnnule> spec=VRendezVousAnnuleSpecification.filter(object);
	        return vRendezVousAnnuleRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous annule", ex);
	    }
	}

	@Override
	public VRendezVousAnnule getVRendezVousAnnuleById(Integer idRendezVous) {
	    Optional<VRendezVousAnnule> vRendezVousAnnule = vRendezVousAnnuleRepository.findById(idRendezVous);
	    if (vRendezVousAnnule.isPresent()) {
	        return vRendezVousAnnule.get();
	    } else {
	        throw new ResourceNotFoundException("VRendezVousAnnule not found with idRendezVous : " + idRendezVous);
	    }
	}






}
