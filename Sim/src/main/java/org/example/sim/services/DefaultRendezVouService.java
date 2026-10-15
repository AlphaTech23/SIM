package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.RendezVou;
import org.example.sim.models.dto.RendezVouSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.RendezVouRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.RendezVouService;
import org.example.sim.specification.RendezVouSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultRendezVouService implements RendezVouService {
	private final RendezVouRepository rendezVouRepository;

	public DefaultRendezVouService(RendezVouRepository rendezVouRepository) {
	   this.rendezVouRepository = rendezVouRepository;
	}

	@Override
	public String exportRendezVouToCSV(List<RendezVou> rendezVou) {
	   return ExportUtils.generateCsv(rendezVou);
	}  

	@Override
	public Page<RendezVou> getAllRendezVou(Pageable pageable) {
	    try {
	        return rendezVouRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving rendez vou", ex);
	    }
	}

	@Override
	public Page<RendezVou> getAllRendezVou(Pageable pageable, RendezVouSearch object) {
	    try {
	        Specification<RendezVou> spec=RendezVouSpecification.filter(object);
	        return rendezVouRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving rendez vou", ex);
	    }
	}

	@Override
	public RendezVou getRendezVouById(Integer id) {
	    Optional<RendezVou> rendezVou = rendezVouRepository.findById(id);
	    if (rendezVou.isPresent()) {
	        return rendezVou.get();
	    } else {
	        throw new ResourceNotFoundException("RendezVou not found with id : " + id);
	    }
	}

	@Override
	public RendezVou createRendezVou(RendezVou rendezVou) {
	    try {
	        return rendezVouRepository.save(rendezVou);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating rendez vou", ex);
	    }
	}

	@Override
	public RendezVou updateRendezVou(Integer id, RendezVou rendezVou) {
	    Optional<RendezVou> existingRendezVou = rendezVouRepository.findById(id);
	    if (existingRendezVou.isPresent()) {
	        rendezVou.setId(id);
	        try {
	            return rendezVouRepository.save(rendezVou);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating rendez vou", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("RendezVou not found with id : " + id);
	    }
	}

	@Override
	public void deleteRendezVou(Integer id) {
	    try {
	        rendezVouRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting rendez vou", ex);
	    }
	}



}
