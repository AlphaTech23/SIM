package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.Membre;
import org.example.sim.models.dto.MembreSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.MembreRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.MembreService;
import org.example.sim.specification.MembreSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.example.sim.repositories.ActivitesMembreRepository;
import org.example.sim.models.ActivitesMembre;
import org.example.sim.repositories.RendezVouRepository;
import org.example.sim.models.RendezVou;
import org.example.sim.repositories.SujetsMembreRepository;
import org.example.sim.models.SujetsMembre;


@Service
public class DefaultMembreService implements MembreService {
	private final MembreRepository membreRepository;
private final ActivitesMembreRepository activitesMembreRepository;
private final RendezVouRepository rendezVouRepository;
private final SujetsMembreRepository sujetsMembreRepository;


	public DefaultMembreService(MembreRepository membreRepository, ActivitesMembreRepository activitesMembreRepository, RendezVouRepository rendezVouRepository, SujetsMembreRepository sujetsMembreRepository) {
	   this.membreRepository = membreRepository;
this.activitesMembreRepository = activitesMembreRepository;
this.rendezVouRepository = rendezVouRepository;
this.sujetsMembreRepository = sujetsMembreRepository;

	}

	@Override
	public String exportMembreToCSV(List<Membre> membre) {
	   return ExportUtils.generateCsv(membre);
	}  

	@Override
	public Page<Membre> getAllMembre(Pageable pageable) {
	    try {
	        return membreRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving membre", ex);
	    }
	}

	@Override
	public Page<Membre> getAllMembre(Pageable pageable, MembreSearch object) {
	    try {
	        Specification<Membre> spec=MembreSpecification.filter(object);
	        return membreRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving membre", ex);
	    }
	}

	@Override
	public Membre getMembreById(Integer id) {
	    Optional<Membre> membre = membreRepository.findById(id);
	    if (membre.isPresent()) {
	        return membre.get();
	    } else {
	        throw new ResourceNotFoundException("Membre not found with id : " + id);
	    }
	}

	@Override
	public Membre createMembre(Membre membre) {
	    try {
	        return membreRepository.save(membre);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating membre", ex);
	    }
	}

	@Override
	public Membre updateMembre(Integer id, Membre membre) {
	    Optional<Membre> existingMembre = membreRepository.findById(id);
	    if (existingMembre.isPresent()) {
	        membre.setId(id);
	        try {
	            return membreRepository.save(membre);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating membre", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Membre not found with id : " + id);
	    }
	}

	@Override
	public void deleteMembre(Integer id) {
	    try {
	        membreRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting membre", ex);
	    }
	}


@Override
@Transactional
public Membre createFullMembre(Membre membre, List<ActivitesMembre> activitesMembres, List<RendezVou> rendezVous, List<SujetsMembre> sujetsMembres) {
  try {
    membre = membreRepository.save(membre);
    
    for (ActivitesMembre activitesMembre : activitesMembres) {
      activitesMembre.setIdmembreMembres(membre);
      activitesMembre.setId(null);
    }
    
    activitesMembreRepository.saveAll(activitesMembres);
    for (RendezVou rendezVou : rendezVous) {
      rendezVou.setIddemandeurMembres(membre);
      rendezVou.setId(null);
    }
    
    rendezVouRepository.saveAll(rendezVous);
    for (SujetsMembre sujetsMembre : sujetsMembres) {
      sujetsMembre.setIdmembreMembres(membre);
      sujetsMembre.setId(null);
    }
    
    sujetsMembreRepository.saveAll(sujetsMembres);
    
    
    return membre;
  } catch (Exception ex) {
    throw new InternalServerErrorException(
    "Error during atomic creation of Membre and its details.", ex);
  }
}
  
@Override
@Transactional
public Membre updateFullMembre(Integer id, Membre membre, List<ActivitesMembre> activitesMembres, List<RendezVou> rendezVous, List<SujetsMembre> sujetsMembres) {
  try {
    if (!membreRepository.existsById(id)) {
      throw new ResourceNotFoundException("Membre not found with id: " + id);
    }

    membre.setId(id);
    membre = membreRepository.save(membre);

    List<ActivitesMembre> existingActivitesMembres = activitesMembreRepository.findByIdmembreMembres(membre);

    List<ActivitesMembre> activitesMembresToDelete = existingActivitesMembres.stream()
      .filter(existing -> activitesMembres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!activitesMembresToDelete.isEmpty()) {
      activitesMembreRepository.deleteAll(activitesMembresToDelete);
    }

    for (ActivitesMembre activitesMembre : activitesMembres) {
      activitesMembre.setIdmembreMembres(membre);
      
      if (activitesMembre.getId() != null && 
          existingActivitesMembres.stream()
            .anyMatch(e -> e.getId().equals(activitesMembre.getId()))) {
          activitesMembreRepository.save(activitesMembre);
      } else {
          activitesMembre.setId(null);
          activitesMembreRepository.save(activitesMembre);
      }
    }
    List<RendezVou> existingRendezVous = rendezVouRepository.findByIddemandeurMembres(membre);

    List<RendezVou> rendezVousToDelete = existingRendezVous.stream()
      .filter(existing -> rendezVous.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!rendezVousToDelete.isEmpty()) {
      rendezVouRepository.deleteAll(rendezVousToDelete);
    }

    for (RendezVou rendezVou : rendezVous) {
      rendezVou.setIddemandeurMembres(membre);
      
      if (rendezVou.getId() != null && 
          existingRendezVous.stream()
            .anyMatch(e -> e.getId().equals(rendezVou.getId()))) {
          rendezVouRepository.save(rendezVou);
      } else {
          rendezVou.setId(null);
          rendezVouRepository.save(rendezVou);
      }
    }
    List<SujetsMembre> existingSujetsMembres = sujetsMembreRepository.findByIdmembreMembres(membre);

    List<SujetsMembre> sujetsMembresToDelete = existingSujetsMembres.stream()
      .filter(existing -> sujetsMembres.stream()
      .noneMatch(current -> current.getId() != null && current.getId().equals(existing.getId())))
      .collect(Collectors.toList());
    if (!sujetsMembresToDelete.isEmpty()) {
      sujetsMembreRepository.deleteAll(sujetsMembresToDelete);
    }

    for (SujetsMembre sujetsMembre : sujetsMembres) {
      sujetsMembre.setIdmembreMembres(membre);
      
      if (sujetsMembre.getId() != null && 
          existingSujetsMembres.stream()
            .anyMatch(e -> e.getId().equals(sujetsMembre.getId()))) {
          sujetsMembreRepository.save(sujetsMembre);
      } else {
          sujetsMembre.setId(null);
          sujetsMembreRepository.save(sujetsMembre);
      }
    }
    
    
    return membre;
  } catch (ResourceNotFoundException ex) {
    throw ex;
  } catch (Exception ex) {
    throw new InternalServerErrorException("Error during atomic update of Membre and its details.", ex);
  }
}
  
  @Override
  @Transactional
  public void deleteFullMembre(Integer id) {
  try {
  Membre membre = membreRepository.findById(id)
  .orElseThrow(() -> new ResourceNotFoundException(
  "Membre not found with id: " + id));
        
        List<ActivitesMembre> activitesMembresToDelete = activitesMembreRepository.findByIdmembreMembres(membre);
        
        if (!activitesMembresToDelete.isEmpty()) {
            activitesMembreRepository.deleteAll(activitesMembresToDelete);
        }
        List<RendezVou> rendezVousToDelete = rendezVouRepository.findByIddemandeurMembres(membre);
        
        if (!rendezVousToDelete.isEmpty()) {
            rendezVouRepository.deleteAll(rendezVousToDelete);
        }
        List<SujetsMembre> sujetsMembresToDelete = sujetsMembreRepository.findByIdmembreMembres(membre);
        
        if (!sujetsMembresToDelete.isEmpty()) {
            sujetsMembreRepository.deleteAll(sujetsMembresToDelete);
        }
        
        
        membreRepository.delete(membre);
        
    } catch (ResourceNotFoundException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new InternalServerErrorException(
            "Error during atomic deletion of Membre and its details.", ex);
}
}
  

}
