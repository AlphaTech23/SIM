package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Activite;
import org.example.sim.models.dto.ActiviteSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.ActiviteRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.ActiviteService;
import org.example.sim.specification.ActiviteSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.ActivitesMembreRepository;
import org.example.sim.models.ActivitesMembre;


@Service
public class DefaultActiviteService implements ActiviteService {
	private final ActiviteRepository activiteRepository;
private final ActivitesMembreRepository activitesMembreRepository;


	public DefaultActiviteService(ActiviteRepository activiteRepository, ActivitesMembreRepository activitesMembreRepository) {
	   this.activiteRepository = activiteRepository;
this.activitesMembreRepository = activitesMembreRepository;

	}

	@Override
	public String exportActiviteToCSV(List<Activite> activite) {
	   return ExportUtils.generateCsv(activite);
	}  

	@Override
	public Page<Activite> getAllActivite(Pageable pageable) {
	    try {
	        return activiteRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving activite", ex);
	    }
	}

	@Override
	public Page<Activite> getAllActivite(Pageable pageable, ActiviteSearch object) {
	    try {
	        Specification<Activite> spec=ActiviteSpecification.filter(object);
	        return activiteRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving activite", ex);
	    }
	}

	@Override
	public Activite getActiviteById(Integer id) {
	    Optional<Activite> activite = activiteRepository.findById(id);
	    if (activite.isPresent()) {
	        return activite.get();
	    } else {
	        throw new ResourceNotFoundException("Activite not found with id : " + id);
	    }
	}

	@Override
	public Activite createActivite(Activite activite) {
	    try {
	        return activiteRepository.save(activite);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating activite", ex);
	    }
	}

	@Override
	public Activite updateActivite(Integer id, Activite activite) {
	    Optional<Activite> existingActivite = activiteRepository.findById(id);
	    if (existingActivite.isPresent()) {
	        activite.setId(id);
	        try {
	            return activiteRepository.save(activite);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating activite", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Activite not found with id : " + id);
	    }
	}

	@Override
	public void deleteActivite(Integer id) {
	    try {
	        activiteRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting activite", ex);
	    }
	}


@Override
@Transactional
public Activite createFullActivite(Activite activite, List<ActivitesMembre> activitesMembres) {
  try {
    activite = activiteRepository.save(activite);
    
    for (ActivitesMembre activitesMembre : activitesMembres) {
      activitesMembre.setIdactiviteActivites(activite);
      activitesMembre.setId(null);
    }
    
    activitesMembreRepository.saveAll(activitesMembres);
    
    
    return activite;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Activite and its details.", ex);
  }
}
  
@Override
@Transactional
public Activite updateFullActivite(Integer id, Activite activite, List<ActivitesMembre> activitesMembres) {
  try {
    if (!activiteRepository.existsById(id)) {
      throw new ResourceNotFoundException("Activite not found with id: " + id);
    }

    activite.setId(id);
    activite = activiteRepository.save(activite);

    List<ActivitesMembre> existingActivitesMembres = activitesMembreRepository.findByIdactiviteActivites(activite);

    List<ActivitesMembre> activitesMembresToDelete = existingActivitesMembres.stream()
      .filter(existing -> activitesMembres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!activitesMembresToDelete.isEmpty()) {
      activitesMembreRepository.deleteAll(activitesMembresToDelete);
    }

    for (ActivitesMembre activitesMembre : activitesMembres) {
      activitesMembre.setIdactiviteActivites(activite);
      
      if (activitesMembre.getId() != null && 
          existingActivitesMembres.stream()
            .anyMatch(e -> e.getId().equals(activitesMembre.getId()))) {
          activitesMembreRepository.save(activitesMembre);
      } else {
          activitesMembre.setId(null);
          activitesMembreRepository.save(activitesMembre);
      }
    }
    
    
    return activite;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Activite and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullActivite(Integer id) {
  try {
  Activite activite = activiteRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Activite not found with id: " + id));
        
        List<ActivitesMembre> activitesMembresToDelete = activitesMembreRepository.findByIdactiviteActivites(activite);
        
        if (!activitesMembresToDelete.isEmpty()) {
            activitesMembreRepository.deleteAll(activitesMembresToDelete);
        }
        
        
        activiteRepository.delete(activite);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Activite and its details.", ex);
}
}
  

}
