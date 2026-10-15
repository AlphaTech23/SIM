package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.VRendezVousEnAttente;
import org.example.sim.models.dto.VRendezVousEnAttenteSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.VRendezVousEnAttenteRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.VRendezVousEnAttenteService;
import org.example.sim.specification.VRendezVousEnAttenteSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVRendezVousEnAttenteService implements VRendezVousEnAttenteService {
	private final VRendezVousEnAttenteRepository vRendezVousEnAttenteRepository;

	public DefaultVRendezVousEnAttenteService(VRendezVousEnAttenteRepository vRendezVousEnAttenteRepository) {
	   this.vRendezVousEnAttenteRepository = vRendezVousEnAttenteRepository;
	}

	@Override
	public String exportVRendezVousEnAttenteToCSV(List<VRendezVousEnAttente> vRendezVousEnAttente) {
	   return ExportUtils.generateCsv(vRendezVousEnAttente);
	}  

	@Override
	public Page<VRendezVousEnAttente> getAllVRendezVousEnAttente(Pageable pageable) {
	    try {
	        return vRendezVousEnAttenteRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous en attente", ex);
	    }
	}

	@Override
	public Page<VRendezVousEnAttente> getAllVRendezVousEnAttente(Pageable pageable, VRendezVousEnAttenteSearch object) {
	    try {
	        Specification<VRendezVousEnAttente> spec=VRendezVousEnAttenteSpecification.filter(object);
	        return vRendezVousEnAttenteRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous en attente", ex);
	    }
	}

	@Override
	public VRendezVousEnAttente getVRendezVousEnAttenteById(Integer idRendezVous) {
	    Optional<VRendezVousEnAttente> vRendezVousEnAttente = vRendezVousEnAttenteRepository.findById(idRendezVous);
	    if (vRendezVousEnAttente.isPresent()) {
	        return vRendezVousEnAttente.get();
	    } else {
	        throw new ResourceNotFoundException("VRendezVousEnAttente not found with idRendezVous : " + idRendezVous);
	    }
	}






}
