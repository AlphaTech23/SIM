package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.dto.ActivitesMembreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.ActivitesMembreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.ActivitesMembreService;
import org.example.sim.specification.ActivitesMembreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultActivitesMembreService implements ActivitesMembreService {
	private final ActivitesMembreRepository activitesMembreRepository;

	public DefaultActivitesMembreService(ActivitesMembreRepository activitesMembreRepository) {
	   this.activitesMembreRepository = activitesMembreRepository;
	}

	@Override
	public String exportActivitesMembreToCSV(List<ActivitesMembre> activitesMembre) {
	   return ExportUtils.generateCsv(activitesMembre);
	}  

	@Override
	public Page<ActivitesMembre> getAllActivitesMembre(Pageable pageable) {
	    try {
	        return activitesMembreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving activites membre", ex);
	    }
	}

	@Override
	public Page<ActivitesMembre> getAllActivitesMembre(Pageable pageable, ActivitesMembreSearch object) {
	    try {
	        Specification<ActivitesMembre> spec=ActivitesMembreSpecification.filter(object);
	        return activitesMembreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving activites membre", ex);
	    }
	}

	@Override
	public ActivitesMembre getActivitesMembreById(Integer id) {
	    Optional<ActivitesMembre> activitesMembre = activitesMembreRepository.findById(id);
	    if (activitesMembre.isPresent()) {
	        return activitesMembre.get();
	    } else {
	        throw new ResourceNotFoundException("ActivitesMembre not found with id : " + id);
	    }
	}

	@Override
	public ActivitesMembre createActivitesMembre(ActivitesMembre activitesMembre) {
	    try {
	        return activitesMembreRepository.save(activitesMembre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating activites membre", ex);
	    }
	}

	@Override
	public ActivitesMembre updateActivitesMembre(Integer id, ActivitesMembre activitesMembre) {
	    Optional<ActivitesMembre> existingActivitesMembre = activitesMembreRepository.findById(id);
	    if (existingActivitesMembre.isPresent()) {
	        activitesMembre.setId(id);
	        try {
	            return activitesMembreRepository.save(activitesMembre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating activites membre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("ActivitesMembre not found with id : " + id);
	    }
	}

	@Override
	public void deleteActivitesMembre(Integer id) {
	    try {
	        activitesMembreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting activites membre", ex);
	    }
	}



}
