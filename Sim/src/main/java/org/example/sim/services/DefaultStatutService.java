package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Statut;
import org.example.sim.models.dto.StatutSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.StatutRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.StatutService;
import org.example.sim.specification.StatutSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.StatutRendezVouRepository;
import org.example.sim.models.StatutRendezVou;


@Service
public class DefaultStatutService implements StatutService {
	private final StatutRepository statutRepository;
private final StatutRendezVouRepository statutRendezVouRepository;


	public DefaultStatutService(StatutRepository statutRepository, StatutRendezVouRepository statutRendezVouRepository) {
	   this.statutRepository = statutRepository;
this.statutRendezVouRepository = statutRendezVouRepository;

	}

	@Override
	public String exportStatutToCSV(List<Statut> statut) {
	   return ExportUtils.generateCsv(statut);
	}  

	@Override
	public Page<Statut> getAllStatut(Pageable pageable) {
	    try {
	        return statutRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut", ex);
	    }
	}

	@Override
	public Page<Statut> getAllStatut(Pageable pageable, StatutSearch object) {
	    try {
	        Specification<Statut> spec=StatutSpecification.filter(object);
	        return statutRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving statut", ex);
	    }
	}

	@Override
	public Statut getStatutById(Integer id) {
	    Optional<Statut> statut = statutRepository.findById(id);
	    if (statut.isPresent()) {
	        return statut.get();
	    } else {
	        throw new ResourceNotFoundException("Statut not found with id : " + id);
	    }
	}

	@Override
	public Statut createStatut(Statut statut) {
	    try {
	        return statutRepository.save(statut);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating statut", ex);
	    }
	}

	@Override
	public Statut updateStatut(Integer id, Statut statut) {
	    Optional<Statut> existingStatut = statutRepository.findById(id);
	    if (existingStatut.isPresent()) {
	        statut.setId(id);
	        try {
	            return statutRepository.save(statut);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating statut", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Statut not found with id : " + id);
	    }
	}

	@Override
	public void deleteStatut(Integer id) {
	    try {
	        statutRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting statut", ex);
	    }
	}


@Override
@Transactional
public Statut createFullStatut(Statut statut, List<StatutRendezVou> statutRendezVous) {
  try {
    statut = statutRepository.save(statut);
    
    for (StatutRendezVou statutRendezVou : statutRendezVous) {
      statutRendezVou.setIdstatutStatut(statut);
      statutRendezVou.setId(null);
    }
    
    statutRendezVouRepository.saveAll(statutRendezVous);
    
    
    return statut;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Statut and its details.", ex);
  }
}
  
@Override
@Transactional
public Statut updateFullStatut(Integer id, Statut statut, List<StatutRendezVou> statutRendezVous) {
  try {
    if (!statutRepository.existsById(id)) {
      throw new ResourceNotFoundException("Statut not found with id: " + id);
    }

    statut.setId(id);
    statut = statutRepository.save(statut);

    List<StatutRendezVou> existingStatutRendezVous = statutRendezVouRepository.findByIdstatutStatut(statut);

    List<StatutRendezVou> statutRendezVousToDelete = existingStatutRendezVous.stream()
      .filter(existing -> statutRendezVous.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!statutRendezVousToDelete.isEmpty()) {
      statutRendezVouRepository.deleteAll(statutRendezVousToDelete);
    }

    for (StatutRendezVou statutRendezVou : statutRendezVous) {
      statutRendezVou.setIdstatutStatut(statut);
      
      if (statutRendezVou.getId() != null && 
          existingStatutRendezVous.stream()
            .anyMatch(e -> e.getId().equals(statutRendezVou.getId()))) {
          statutRendezVouRepository.save(statutRendezVou);
      } else {
          statutRendezVou.setId(null);
          statutRendezVouRepository.save(statutRendezVou);
      }
    }
    
    
    return statut;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Statut and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullStatut(Integer id) {
  try {
  Statut statut = statutRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Statut not found with id: " + id));
        
        List<StatutRendezVou> statutRendezVousToDelete = statutRendezVouRepository.findByIdstatutStatut(statut);
        
        if (!statutRendezVousToDelete.isEmpty()) {
            statutRendezVouRepository.deleteAll(statutRendezVousToDelete);
        }
        
        
        statutRepository.delete(statut);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Statut and its details.", ex);
}
}
  

}
