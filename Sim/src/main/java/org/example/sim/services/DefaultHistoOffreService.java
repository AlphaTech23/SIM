package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.HistoOffre;
import org.example.sim.models.dto.HistoOffreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.HistoOffreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.HistoOffreService;
import org.example.sim.specification.HistoOffreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultHistoOffreService implements HistoOffreService {
	private final HistoOffreRepository histoOffreRepository;

	public DefaultHistoOffreService(HistoOffreRepository histoOffreRepository) {
	   this.histoOffreRepository = histoOffreRepository;
	}

	@Override
	public String exportHistoOffreToCSV(List<HistoOffre> histoOffre) {
	   return ExportUtils.generateCsv(histoOffre);
	}  

	@Override
	public Page<HistoOffre> getAllHistoOffre(Pageable pageable) {
	    try {
	        return histoOffreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving histo offre", ex);
	    }
	}

	@Override
	public Page<HistoOffre> getAllHistoOffre(Pageable pageable, HistoOffreSearch object) {
	    try {
	        Specification<HistoOffre> spec=HistoOffreSpecification.filter(object);
	        return histoOffreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving histo offre", ex);
	    }
	}

	@Override
	public HistoOffre getHistoOffreById(Integer id) {
	    Optional<HistoOffre> histoOffre = histoOffreRepository.findById(id);
	    if (histoOffre.isPresent()) {
	        return histoOffre.get();
	    } else {
	        throw new ResourceNotFoundException("HistoOffre not found with id : " + id);
	    }
	}

	@Override
	public HistoOffre createHistoOffre(HistoOffre histoOffre) {
	    try {
	        return histoOffreRepository.save(histoOffre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating histo offre", ex);
	    }
	}

	@Override
	public HistoOffre updateHistoOffre(Integer id, HistoOffre histoOffre) {
	    Optional<HistoOffre> existingHistoOffre = histoOffreRepository.findById(id);
	    if (existingHistoOffre.isPresent()) {
	        histoOffre.setId(id);
	        try {
	            return histoOffreRepository.save(histoOffre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating histo offre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("HistoOffre not found with id : " + id);
	    }
	}

	@Override
	public void deleteHistoOffre(Integer id) {
	    try {
	        histoOffreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting histo offre", ex);
	    }
	}



}
