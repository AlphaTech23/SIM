package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Sujet;
import org.example.sim.models.dto.SujetSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.SujetRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.SujetService;
import org.example.sim.specification.SujetSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.SujetsMembreRepository;
import org.example.sim.models.SujetsMembre;


@Service
public class DefaultSujetService implements SujetService {
	private final SujetRepository sujetRepository;
private final SujetsMembreRepository sujetsMembreRepository;


	public DefaultSujetService(SujetRepository sujetRepository, SujetsMembreRepository sujetsMembreRepository) {
	   this.sujetRepository = sujetRepository;
this.sujetsMembreRepository = sujetsMembreRepository;

	}

	@Override
	public String exportSujetToCSV(List<Sujet> sujet) {
	   return ExportUtils.generateCsv(sujet);
	}  

	@Override
	public Page<Sujet> getAllSujet(Pageable pageable) {
	    try {
	        return sujetRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving sujet", ex);
	    }
	}

	@Override
	public Page<Sujet> getAllSujet(Pageable pageable, SujetSearch object) {
	    try {
	        Specification<Sujet> spec=SujetSpecification.filter(object);
	        return sujetRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving sujet", ex);
	    }
	}

	@Override
	public Sujet getSujetById(Integer id) {
	    Optional<Sujet> sujet = sujetRepository.findById(id);
	    if (sujet.isPresent()) {
	        return sujet.get();
	    } else {
	        throw new ResourceNotFoundException("Sujet not found with id : " + id);
	    }
	}

	@Override
	public Sujet createSujet(Sujet sujet) {
	    try {
	        return sujetRepository.save(sujet);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating sujet", ex);
	    }
	}

	@Override
	public Sujet updateSujet(Integer id, Sujet sujet) {
	    Optional<Sujet> existingSujet = sujetRepository.findById(id);
	    if (existingSujet.isPresent()) {
	        sujet.setId(id);
	        try {
	            return sujetRepository.save(sujet);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating sujet", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Sujet not found with id : " + id);
	    }
	}

	@Override
	public void deleteSujet(Integer id) {
	    try {
	        sujetRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting sujet", ex);
	    }
	}


@Override
@Transactional
public Sujet createFullSujet(Sujet sujet, List<SujetsMembre> sujetsMembres) {
  try {
    sujet = sujetRepository.save(sujet);
    
    for (SujetsMembre sujetsMembre : sujetsMembres) {
      sujetsMembre.setIdsujetSujets(sujet);
      sujetsMembre.setId(null);
    }
    
    sujetsMembreRepository.saveAll(sujetsMembres);
    
    
    return sujet;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Sujet and its details.", ex);
  }
}
  
@Override
@Transactional
public Sujet updateFullSujet(Integer id, Sujet sujet, List<SujetsMembre> sujetsMembres) {
  try {
    if (!sujetRepository.existsById(id)) {
      throw new ResourceNotFoundException("Sujet not found with id: " + id);
    }

    sujet.setId(id);
    sujet = sujetRepository.save(sujet);

    List<SujetsMembre> existingSujetsMembres = sujetsMembreRepository.findByIdsujetSujets(sujet);

    List<SujetsMembre> sujetsMembresToDelete = existingSujetsMembres.stream()
      .filter(existing -> sujetsMembres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!sujetsMembresToDelete.isEmpty()) {
      sujetsMembreRepository.deleteAll(sujetsMembresToDelete);
    }

    for (SujetsMembre sujetsMembre : sujetsMembres) {
      sujetsMembre.setIdsujetSujets(sujet);
      
      if (sujetsMembre.getId() != null && 
          existingSujetsMembres.stream()
            .anyMatch(e -> e.getId().equals(sujetsMembre.getId()))) {
          sujetsMembreRepository.save(sujetsMembre);
      } else {
          sujetsMembre.setId(null);
          sujetsMembreRepository.save(sujetsMembre);
      }
    }
    
    
    return sujet;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Sujet and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullSujet(Integer id) {
  try {
  Sujet sujet = sujetRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Sujet not found with id: " + id));
        
        List<SujetsMembre> sujetsMembresToDelete = sujetsMembreRepository.findByIdsujetSujets(sujet);
        
        if (!sujetsMembresToDelete.isEmpty()) {
            sujetsMembreRepository.deleteAll(sujetsMembresToDelete);
        }
        
        
        sujetRepository.delete(sujet);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Sujet and its details.", ex);
}
}
  

}
