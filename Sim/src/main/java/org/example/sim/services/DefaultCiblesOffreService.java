package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.CiblesOffre;
import org.example.sim.models.dto.CiblesOffreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.CiblesOffreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.CiblesOffreService;
import org.example.sim.specification.CiblesOffreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultCiblesOffreService implements CiblesOffreService {
	private final CiblesOffreRepository ciblesOffreRepository;

	public DefaultCiblesOffreService(CiblesOffreRepository ciblesOffreRepository) {
	   this.ciblesOffreRepository = ciblesOffreRepository;
	}

	@Override
	public String exportCiblesOffreToCSV(List<CiblesOffre> ciblesOffre) {
	   return ExportUtils.generateCsv(ciblesOffre);
	}  

	@Override
	public Page<CiblesOffre> getAllCiblesOffre(Pageable pageable) {
	    try {
	        return ciblesOffreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving cibles offre", ex);
	    }
	}

	@Override
	public Page<CiblesOffre> getAllCiblesOffre(Pageable pageable, CiblesOffreSearch object) {
	    try {
	        Specification<CiblesOffre> spec=CiblesOffreSpecification.filter(object);
	        return ciblesOffreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving cibles offre", ex);
	    }
	}

	@Override
	public CiblesOffre getCiblesOffreById(Integer id) {
	    Optional<CiblesOffre> ciblesOffre = ciblesOffreRepository.findById(id);
	    if (ciblesOffre.isPresent()) {
	        return ciblesOffre.get();
	    } else {
	        throw new ResourceNotFoundException("CiblesOffre not found with id : " + id);
	    }
	}

	@Override
	public CiblesOffre createCiblesOffre(CiblesOffre ciblesOffre) {
	    try {
	        return ciblesOffreRepository.save(ciblesOffre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating cibles offre", ex);
	    }
	}

	@Override
	public CiblesOffre updateCiblesOffre(Integer id, CiblesOffre ciblesOffre) {
	    Optional<CiblesOffre> existingCiblesOffre = ciblesOffreRepository.findById(id);
	    if (existingCiblesOffre.isPresent()) {
	        ciblesOffre.setId(id);
	        try {
	            return ciblesOffreRepository.save(ciblesOffre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating cibles offre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("CiblesOffre not found with id : " + id);
	    }
	}

	@Override
	public void deleteCiblesOffre(Integer id) {
	    try {
	        ciblesOffreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting cibles offre", ex);
	    }
	}



}
