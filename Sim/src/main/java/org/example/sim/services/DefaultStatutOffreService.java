package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.StatutOffre;
import org.example.sim.models.dto.StatutOffreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.StatutOffreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.StatutOffreService;
import org.example.sim.specification.StatutOffreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultStatutOffreService implements StatutOffreService {
	private final StatutOffreRepository statutOffreRepository;

	public DefaultStatutOffreService(StatutOffreRepository statutOffreRepository) {
	   this.statutOffreRepository = statutOffreRepository;
	}

	@Override
	public String exportStatutOffreToCSV(List<StatutOffre> statutOffre) {
	   return ExportUtils.generateCsv(statutOffre);
	}  

	@Override
	public Page<StatutOffre> getAllStatutOffre(Pageable pageable) {
	    try {
	        return statutOffreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut offre", ex);
	    }
	}

	@Override
	public Page<StatutOffre> getAllStatutOffre(Pageable pageable, StatutOffreSearch object) {
	    try {
	        Specification<StatutOffre> spec=StatutOffreSpecification.filter(object);
	        return statutOffreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut offre", ex);
	    }
	}

	@Override
	public StatutOffre getStatutOffreById(Integer id) {
	    Optional<StatutOffre> statutOffre = statutOffreRepository.findById(id);
	    if (statutOffre.isPresent()) {
	        return statutOffre.get();
	    } else {
	        throw new ResourceNotFoundException("StatutOffre not found with id : " + id);
	    }
	}

	@Override
	public StatutOffre createStatutOffre(StatutOffre statutOffre) {
	    try {
	        return statutOffreRepository.save(statutOffre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating statut offre", ex);
	    }
	}

	@Override
	public StatutOffre updateStatutOffre(Integer id, StatutOffre statutOffre) {
	    Optional<StatutOffre> existingStatutOffre = statutOffreRepository.findById(id);
	    if (existingStatutOffre.isPresent()) {
	        statutOffre.setId(id);
	        try {
	            return statutOffreRepository.save(statutOffre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating statut offre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("StatutOffre not found with id : " + id);
	    }
	}

	@Override
	public void deleteStatutOffre(Integer id) {
	    try {
	        statutOffreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting statut offre", ex);
	    }
	}



}
