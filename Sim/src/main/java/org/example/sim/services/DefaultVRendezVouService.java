package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.VRendezVou;
import org.example.sim.models.dto.VRendezVouSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.VRendezVouRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.VRendezVouService;
import org.example.sim.specification.VRendezVouSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVRendezVouService implements VRendezVouService {
	private final VRendezVouRepository vRendezVouRepository;

	public DefaultVRendezVouService(VRendezVouRepository vRendezVouRepository) {
	   this.vRendezVouRepository = vRendezVouRepository;
	}

	@Override
	public String exportVRendezVouToCSV(List<VRendezVou> vRendezVou) {
	   return ExportUtils.generateCsv(vRendezVou);
	}  

	@Override
	public Page<VRendezVou> getAllVRendezVou(Pageable pageable) {
	    try {
	        return vRendezVouRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vou", ex);
	    }
	}

	@Override
	public Page<VRendezVou> getAllVRendezVou(Pageable pageable, VRendezVouSearch object) {
	    try {
	        Specification<VRendezVou> spec=VRendezVouSpecification.filter(object);
	        return vRendezVouRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v rendez vou", ex);
	    }
	}

	@Override
	public VRendezVou getVRendezVouById(Integer idRendezVous) {
	    Optional<VRendezVou> vRendezVou = vRendezVouRepository.findById(idRendezVous);
	    if (vRendezVou.isPresent()) {
	        return vRendezVou.get();
	    } else {
	        throw new ResourceNotFoundException("VRendezVou not found with idRendezVous : " + idRendezVous);
	    }
	}






}
