package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.VRendezVousAccepte;
import org.example.sim.models.dto.VRendezVousAccepteSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.VRendezVousAccepteRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.VRendezVousAccepteService;
import org.example.sim.specification.VRendezVousAccepteSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVRendezVousAccepteService implements VRendezVousAccepteService {
	private final VRendezVousAccepteRepository vRendezVousAccepteRepository;

	public DefaultVRendezVousAccepteService(VRendezVousAccepteRepository vRendezVousAccepteRepository) {
	   this.vRendezVousAccepteRepository = vRendezVousAccepteRepository;
	}

	@Override
	public String exportVRendezVousAccepteToCSV(List<VRendezVousAccepte> vRendezVousAccepte) {
	   return ExportUtils.generateCsv(vRendezVousAccepte);
	}  

	@Override
	public Page<VRendezVousAccepte> getAllVRendezVousAccepte(Pageable pageable) {
	    try {
	        return vRendezVousAccepteRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous accepte", ex);
	    }
	}

	@Override
	public Page<VRendezVousAccepte> getAllVRendezVousAccepte(Pageable pageable, VRendezVousAccepteSearch object) {
	    try {
	        Specification<VRendezVousAccepte> spec=VRendezVousAccepteSpecification.filter(object);
	        return vRendezVousAccepteRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous accepte", ex);
	    }
	}

	@Override
	public VRendezVousAccepte getVRendezVousAccepteById(Integer idRendezVous) {
	    Optional<VRendezVousAccepte> vRendezVousAccepte = vRendezVousAccepteRepository.findById(idRendezVous);
	    if (vRendezVousAccepte.isPresent()) {
	        return vRendezVousAccepte.get();
	    } else {
	        throw new ResourceNotFoundException("VRendezVousAccepte not found with idRendezVous : " + idRendezVous);
	    }
	}






}
