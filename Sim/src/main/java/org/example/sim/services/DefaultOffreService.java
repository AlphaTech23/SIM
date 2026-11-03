package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Offre;
import org.example.sim.models.dto.OffreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.OffreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.OffreService;
import org.example.sim.specification.OffreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.CiblesOffreRepository;
import org.example.sim.models.CiblesOffre;
import org.example.sim.repositories.HistoOffreRepository;
import org.example.sim.models.HistoOffre;


@Service
public class DefaultOffreService implements OffreService {
	private final OffreRepository offreRepository;
private final CiblesOffreRepository ciblesOffreRepository;
private final HistoOffreRepository histoOffreRepository;


	public DefaultOffreService(OffreRepository offreRepository, CiblesOffreRepository ciblesOffreRepository, HistoOffreRepository histoOffreRepository) {
	   this.offreRepository = offreRepository;
this.ciblesOffreRepository = ciblesOffreRepository;
this.histoOffreRepository = histoOffreRepository;

	}

	@Override
	public String exportOffreToCSV(List<Offre> offre) {
	   return ExportUtils.generateCsv(offre);
	}  

	@Override
	public Page<Offre> getAllOffre(Pageable pageable) {
	    try {
	        return offreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving offre", ex);
	    }
	}

	@Override
	public Page<Offre> getAllOffre(Pageable pageable, OffreSearch object) {
	    try {
	        Specification<Offre> spec=OffreSpecification.filter(object);
	        return offreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving offre", ex);
	    }
	}

	@Override
	public Offre getOffreById(Integer id) {
	    Optional<Offre> offre = offreRepository.findById(id);
	    if (offre.isPresent()) {
	        return offre.get();
	    } else {
	        throw new ResourceNotFoundException("Offre not found with id : " + id);
	    }
	}

	@Override
	public Offre createOffre(Offre offre) {
	    try {
	        return offreRepository.save(offre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating offre", ex);
	    }
	}

	@Override
	public Offre updateOffre(Integer id, Offre offre) {
	    Optional<Offre> existingOffre = offreRepository.findById(id);
	    if (existingOffre.isPresent()) {
	        offre.setId(id);
	        try {
	            return offreRepository.save(offre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating offre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Offre not found with id : " + id);
	    }
	}

	@Override
	public void deleteOffre(Integer id) {
	    try {
	        offreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting offre", ex);
	    }
	}


@Override
@Transactional
public Offre createFullOffre(Offre offre, List<CiblesOffre> ciblesOffres, List<HistoOffre> histoOffres) {
  try {
    offre = offreRepository.save(offre);
    
    for (CiblesOffre ciblesOffre : ciblesOffres) {
      ciblesOffre.setIdoffreOffres(offre);
      ciblesOffre.setId(null);
    }
    
    ciblesOffreRepository.saveAll(ciblesOffres);
    for (HistoOffre histoOffre : histoOffres) {
      histoOffre.setIdoffreOffres(offre);
      histoOffre.setId(null);
    }
    
    histoOffreRepository.saveAll(histoOffres);
    
    
    return offre;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Offre and its details.", ex);
  }
}
  
@Override
@Transactional
public Offre updateFullOffre(Integer id, Offre offre, List<CiblesOffre> ciblesOffres, List<HistoOffre> histoOffres) {
  try {
    if (!offreRepository.existsById(id)) {
      throw new ResourceNotFoundException("Offre not found with id: " + id);
    }

    offre.setId(id);
    offre = offreRepository.save(offre);

    List<CiblesOffre> existingCiblesOffres = ciblesOffreRepository.findByIdoffreOffres(offre);

    List<CiblesOffre> ciblesOffresToDelete = existingCiblesOffres.stream()
      .filter(existing -> ciblesOffres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!ciblesOffresToDelete.isEmpty()) {
      ciblesOffreRepository.deleteAll(ciblesOffresToDelete);
    }

    for (CiblesOffre ciblesOffre : ciblesOffres) {
      ciblesOffre.setIdoffreOffres(offre);
      
      if (ciblesOffre.getId() != null && 
          existingCiblesOffres.stream()
            .anyMatch(e -> e.getId().equals(ciblesOffre.getId()))) {
          ciblesOffreRepository.save(ciblesOffre);
      } else {
          ciblesOffre.setId(null);
          ciblesOffreRepository.save(ciblesOffre);
      }
    }
    List<HistoOffre> existingHistoOffres = histoOffreRepository.findByIdoffreOffres(offre);

    List<HistoOffre> histoOffresToDelete = existingHistoOffres.stream()
      .filter(existing -> histoOffres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!histoOffresToDelete.isEmpty()) {
      histoOffreRepository.deleteAll(histoOffresToDelete);
    }

    for (HistoOffre histoOffre : histoOffres) {
      histoOffre.setIdoffreOffres(offre);
      
      if (histoOffre.getId() != null && 
          existingHistoOffres.stream()
            .anyMatch(e -> e.getId().equals(histoOffre.getId()))) {
          histoOffreRepository.save(histoOffre);
      } else {
          histoOffre.setId(null);
          histoOffreRepository.save(histoOffre);
      }
    }
    
    
    return offre;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Offre and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullOffre(Integer id) {
  try {
  Offre offre = offreRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Offre not found with id: " + id));
        
        List<CiblesOffre> ciblesOffresToDelete = ciblesOffreRepository.findByIdoffreOffres(offre);
        
        if (!ciblesOffresToDelete.isEmpty()) {
            ciblesOffreRepository.deleteAll(ciblesOffresToDelete);
        }
        List<HistoOffre> histoOffresToDelete = histoOffreRepository.findByIdoffreOffres(offre);
        
        if (!histoOffresToDelete.isEmpty()) {
            histoOffreRepository.deleteAll(histoOffresToDelete);
        }
        
        
        offreRepository.delete(offre);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Offre and its details.", ex);
}
}
  

}
