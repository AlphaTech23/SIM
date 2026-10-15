package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.SujetsMembre;
import org.example.sim.models.dto.SujetsMembreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.SujetsMembreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.SujetsMembreService;
import org.example.sim.specification.SujetsMembreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultSujetsMembreService implements SujetsMembreService {
	private final SujetsMembreRepository sujetsMembreRepository;

	public DefaultSujetsMembreService(SujetsMembreRepository sujetsMembreRepository) {
	   this.sujetsMembreRepository = sujetsMembreRepository;
	}

	@Override
	public String exportSujetsMembreToCSV(List<SujetsMembre> sujetsMembre) {
	   return ExportUtils.generateCsv(sujetsMembre);
	}  

	@Override
	public Page<SujetsMembre> getAllSujetsMembre(Pageable pageable) {
	    try {
	        return sujetsMembreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving sujets membre", ex);
	    }
	}

	@Override
	public Page<SujetsMembre> getAllSujetsMembre(Pageable pageable, SujetsMembreSearch object) {
	    try {
	        Specification<SujetsMembre> spec=SujetsMembreSpecification.filter(object);
	        return sujetsMembreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving sujets membre", ex);
	    }
	}

	@Override
	public SujetsMembre getSujetsMembreById(Integer id) {
	    Optional<SujetsMembre> sujetsMembre = sujetsMembreRepository.findById(id);
	    if (sujetsMembre.isPresent()) {
	        return sujetsMembre.get();
	    } else {
	        throw new ResourceNotFoundException("SujetsMembre not found with id : " + id);
	    }
	}

	@Override
	public SujetsMembre createSujetsMembre(SujetsMembre sujetsMembre) {
	    try {
	        return sujetsMembreRepository.save(sujetsMembre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating sujets membre", ex);
	    }
	}

	@Override
	public SujetsMembre updateSujetsMembre(Integer id, SujetsMembre sujetsMembre) {
	    Optional<SujetsMembre> existingSujetsMembre = sujetsMembreRepository.findById(id);
	    if (existingSujetsMembre.isPresent()) {
	        sujetsMembre.setId(id);
	        try {
	            return sujetsMembreRepository.save(sujetsMembre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating sujets membre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("SujetsMembre not found with id : " + id);
	    }
	}

	@Override
	public void deleteSujetsMembre(Integer id) {
	    try {
	        sujetsMembreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting sujets membre", ex);
	    }
	}



}
