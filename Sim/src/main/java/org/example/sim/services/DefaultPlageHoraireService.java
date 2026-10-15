package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.PlageHoraire;
import org.example.sim.models.dto.PlageHoraireSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.PlageHoraireRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.PlageHoraireService;
import org.example.sim.specification.PlageHoraireSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultPlageHoraireService implements PlageHoraireService {
	private final PlageHoraireRepository plageHoraireRepository;

	public DefaultPlageHoraireService(PlageHoraireRepository plageHoraireRepository) {
	   this.plageHoraireRepository = plageHoraireRepository;
	}

	@Override
	public String exportPlageHoraireToCSV(List<PlageHoraire> plageHoraire) {
	   return ExportUtils.generateCsv(plageHoraire);
	}  

	@Override
	public Page<PlageHoraire> getAllPlageHoraire(Pageable pageable) {
	    try {
	        return plageHoraireRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving plage horaire", ex);
	    }
	}

	@Override
	public Page<PlageHoraire> getAllPlageHoraire(Pageable pageable, PlageHoraireSearch object) {
	    try {
	        Specification<PlageHoraire> spec=PlageHoraireSpecification.filter(object);
	        return plageHoraireRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving plage horaire", ex);
	    }
	}

	@Override
	public PlageHoraire getPlageHoraireById(Integer id) {
	    Optional<PlageHoraire> plageHoraire = plageHoraireRepository.findById(id);
	    if (plageHoraire.isPresent()) {
	        return plageHoraire.get();
	    } else {
	        throw new ResourceNotFoundException("PlageHoraire not found with id : " + id);
	    }
	}

	@Override
	public PlageHoraire createPlageHoraire(PlageHoraire plageHoraire) {
	    try {
	        return plageHoraireRepository.save(plageHoraire);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating plage horaire", ex);
	    }
	}

	@Override
	public PlageHoraire updatePlageHoraire(Integer id, PlageHoraire plageHoraire) {
	    Optional<PlageHoraire> existingPlageHoraire = plageHoraireRepository.findById(id);
	    if (existingPlageHoraire.isPresent()) {
	        plageHoraire.setId(id);
	        try {
	            return plageHoraireRepository.save(plageHoraire);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating plage horaire", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("PlageHoraire not found with id : " + id);
	    }
	}

	@Override
	public void deletePlageHoraire(Integer id) {
	    try {
	        plageHoraireRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting plage horaire", ex);
	    }
	}



}
