package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.TypeOffre;
import org.example.sim.models.dto.TypeOffreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.TypeOffreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.TypeOffreService;
import org.example.sim.specification.TypeOffreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultTypeOffreService implements TypeOffreService {
	private final TypeOffreRepository typeOffreRepository;

	public DefaultTypeOffreService(TypeOffreRepository typeOffreRepository) {
	   this.typeOffreRepository = typeOffreRepository;
	}

	@Override
	public String exportTypeOffreToCSV(List<TypeOffre> typeOffre) {
	   return ExportUtils.generateCsv(typeOffre);
	}  

	@Override
	public Page<TypeOffre> getAllTypeOffre(Pageable pageable) {
	    try {
	        return typeOffreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving type offre", ex);
	    }
	}

	@Override
	public Page<TypeOffre> getAllTypeOffre(Pageable pageable, TypeOffreSearch object) {
	    try {
	        Specification<TypeOffre> spec=TypeOffreSpecification.filter(object);
	        return typeOffreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving type offre", ex);
	    }
	}

	@Override
	public TypeOffre getTypeOffreById(Integer id) {
	    Optional<TypeOffre> typeOffre = typeOffreRepository.findById(id);
	    if (typeOffre.isPresent()) {
	        return typeOffre.get();
	    } else {
	        throw new ResourceNotFoundException("TypeOffre not found with id : " + id);
	    }
	}

	@Override
	public TypeOffre createTypeOffre(TypeOffre typeOffre) {
	    try {
	        return typeOffreRepository.save(typeOffre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating type offre", ex);
	    }
	}

	@Override
	public TypeOffre updateTypeOffre(Integer id, TypeOffre typeOffre) {
	    Optional<TypeOffre> existingTypeOffre = typeOffreRepository.findById(id);
	    if (existingTypeOffre.isPresent()) {
	        typeOffre.setId(id);
	        try {
	            return typeOffreRepository.save(typeOffre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating type offre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("TypeOffre not found with id : " + id);
	    }
	}

	@Override
	public void deleteTypeOffre(Integer id) {
	    try {
	        typeOffreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting type offre", ex);
	    }
	}



}
