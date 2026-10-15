package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.StatutRendezVou;
import org.example.sim.models.dto.StatutRendezVouSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.StatutRendezVouRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.StatutRendezVouService;
import org.example.sim.specification.StatutRendezVouSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultStatutRendezVouService implements StatutRendezVouService {
	private final StatutRendezVouRepository statutRendezVouRepository;

	public DefaultStatutRendezVouService(StatutRendezVouRepository statutRendezVouRepository) {
	   this.statutRendezVouRepository = statutRendezVouRepository;
	}

	@Override
	public String exportStatutRendezVouToCSV(List<StatutRendezVou> statutRendezVou) {
	   return ExportUtils.generateCsv(statutRendezVou);
	}  

	@Override
	public Page<StatutRendezVou> getAllStatutRendezVou(Pageable pageable) {
	    try {
	        return statutRendezVouRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut rendez vou", ex);
	    }
	}

	@Override
	public Page<StatutRendezVou> getAllStatutRendezVou(Pageable pageable, StatutRendezVouSearch object) {
	    try {
	        Specification<StatutRendezVou> spec=StatutRendezVouSpecification.filter(object);
	        return statutRendezVouRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut rendez vou", ex);
	    }
	}

	@Override
	public StatutRendezVou getStatutRendezVouById(Integer id) {
	    Optional<StatutRendezVou> statutRendezVou = statutRendezVouRepository.findById(id);
	    if (statutRendezVou.isPresent()) {
	        return statutRendezVou.get();
	    } else {
	        throw new ResourceNotFoundException("StatutRendezVou not found with id : " + id);
	    }
	}

	@Override
	public StatutRendezVou createStatutRendezVou(StatutRendezVou statutRendezVou) {
	    try {
	        return statutRendezVouRepository.save(statutRendezVou);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating statut rendez vou", ex);
	    }
	}

	@Override
	public StatutRendezVou updateStatutRendezVou(Integer id, StatutRendezVou statutRendezVou) {
	    Optional<StatutRendezVou> existingStatutRendezVou = statutRendezVouRepository.findById(id);
	    if (existingStatutRendezVou.isPresent()) {
	        statutRendezVou.setId(id);
	        try {
	            return statutRendezVouRepository.save(statutRendezVou);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating statut rendez vou", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("StatutRendezVou not found with id : " + id);
	    }
	}

	@Override
	public void deleteStatutRendezVou(Integer id) {
	    try {
	        statutRendezVouRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting statut rendez vou", ex);
	    }
	}



}
