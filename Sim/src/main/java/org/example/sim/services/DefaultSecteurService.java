package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Secteur;
import org.example.sim.models.dto.SecteurSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.SecteurRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.SecteurService;
import org.example.sim.specification.SecteurSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.MembreRepository;
import org.example.sim.models.Membre;


@Service
public class DefaultSecteurService implements SecteurService {
	private final SecteurRepository secteurRepository;
private final MembreRepository membreRepository;


	public DefaultSecteurService(SecteurRepository secteurRepository, MembreRepository membreRepository) {
	   this.secteurRepository = secteurRepository;
this.membreRepository = membreRepository;

	}

	@Override
	public String exportSecteurToCSV(List<Secteur> secteur) {
	   return ExportUtils.generateCsv(secteur);
	}  

	@Override
	public Page<Secteur> getAllSecteur(Pageable pageable) {
	    try {
	        return secteurRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving secteur", ex);
	    }
	}

	@Override
	public Page<Secteur> getAllSecteur(Pageable pageable, SecteurSearch object) {
	    try {
	        Specification<Secteur> spec=SecteurSpecification.filter(object);
	        return secteurRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving secteur", ex);
	    }
	}

	@Override
	public Secteur getSecteurById(Integer id) {
	    Optional<Secteur> secteur = secteurRepository.findById(id);
	    if (secteur.isPresent()) {
	        return secteur.get();
	    } else {
	        throw new ResourceNotFoundException("Secteur not found with id : " + id);
	    }
	}

	@Override
	public Secteur createSecteur(Secteur secteur) {
	    try {
	        return secteurRepository.save(secteur);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating secteur", ex);
	    }
	}

	@Override
	public Secteur updateSecteur(Integer id, Secteur secteur) {
	    Optional<Secteur> existingSecteur = secteurRepository.findById(id);
	    if (existingSecteur.isPresent()) {
	        secteur.setId(id);
	        try {
	            return secteurRepository.save(secteur);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating secteur", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Secteur not found with id : " + id);
	    }
	}

	@Override
	public void deleteSecteur(Integer id) {
	    try {
	        secteurRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting secteur", ex);
	    }
	}


@Override
@Transactional
public Secteur createFullSecteur(Secteur secteur, List<Membre> membres) {
  try {
    secteur = secteurRepository.save(secteur);
    
    for (Membre membre : membres) {
      membre.setIdsecteurSecteurs(secteur);
      membre.setId(null);
    }
    
    membreRepository.saveAll(membres);
    
    
    return secteur;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Secteur and its details.", ex);
  }
}
  
@Override
@Transactional
public Secteur updateFullSecteur(Integer id, Secteur secteur, List<Membre> membres) {
  try {
    if (!secteurRepository.existsById(id)) {
      throw new ResourceNotFoundException("Secteur not found with id: " + id);
    }

    secteur.setId(id);
    secteur = secteurRepository.save(secteur);

    List<Membre> existingMembres = membreRepository.findByIdsecteurSecteurs(secteur);

    List<Membre> membresToDelete = existingMembres.stream()
      .filter(existing -> membres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!membresToDelete.isEmpty()) {
      membreRepository.deleteAll(membresToDelete);
    }

    for (Membre membre : membres) {
      membre.setIdsecteurSecteurs(secteur);
      
      if (membre.getId() != null && 
          existingMembres.stream()
            .anyMatch(e -> e.getId().equals(membre.getId()))) {
          membreRepository.save(membre);
      } else {
          membre.setId(null);
          membreRepository.save(membre);
      }
    }
    
    
    return secteur;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Secteur and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullSecteur(Integer id) {
  try {
  Secteur secteur = secteurRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Secteur not found with id: " + id));
        
        List<Membre> membresToDelete = membreRepository.findByIdsecteurSecteurs(secteur);
        
        if (!membresToDelete.isEmpty()) {
            membreRepository.deleteAll(membresToDelete);
        }
        
        
        secteurRepository.delete(secteur);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Secteur and its details.", ex);
}
}
  

}
