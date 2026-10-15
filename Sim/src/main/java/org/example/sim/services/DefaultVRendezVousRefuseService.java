package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.VRendezVousRefuse;
import org.example.sim.models.dto.VRendezVousRefuseSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.VRendezVousRefuseRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.VRendezVousRefuseService;
import org.example.sim.specification.VRendezVousRefuseSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVRendezVousRefuseService implements VRendezVousRefuseService {
	private final VRendezVousRefuseRepository vRendezVousRefuseRepository;

	public DefaultVRendezVousRefuseService(VRendezVousRefuseRepository vRendezVousRefuseRepository) {
	   this.vRendezVousRefuseRepository = vRendezVousRefuseRepository;
	}

	@Override
	public String exportVRendezVousRefuseToCSV(List<VRendezVousRefuse> vRendezVousRefuse) {
	   return ExportUtils.generateCsv(vRendezVousRefuse);
	}  

	@Override
	public Page<VRendezVousRefuse> getAllVRendezVousRefuse(Pageable pageable) {
	    try {
	        return vRendezVousRefuseRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous refuse", ex);
	    }
	}

	@Override
	public Page<VRendezVousRefuse> getAllVRendezVousRefuse(Pageable pageable, VRendezVousRefuseSearch object) {
	    try {
	        Specification<VRendezVousRefuse> spec=VRendezVousRefuseSpecification.filter(object);
	        return vRendezVousRefuseRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vous refuse", ex);
	    }
	}

	@Override
	public VRendezVousRefuse getVRendezVousRefuseById(Integer idRendezVous) {
	    Optional<VRendezVousRefuse> vRendezVousRefuse = vRendezVousRefuseRepository.findById(idRendezVous);
	    if (vRendezVousRefuse.isPresent()) {
	        return vRendezVousRefuse.get();
	    } else {
	        throw new ResourceNotFoundException("VRendezVousRefuse not found with idRendezVous : " + idRendezVous);
	    }
	}






}
