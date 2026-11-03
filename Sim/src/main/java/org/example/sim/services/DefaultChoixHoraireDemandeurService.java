package org.example.sim.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.example.sim.models.ChoixHoraireDemandeur;
import org.example.sim.models.dto.ChoixHoraireDemandeurSearch;
import org.springframework.stereotype.Service;
import org.example.sim.repositories.ChoixHoraireDemandeurRepository;
import java.util.Optional;
import org.example.sim.services.interfaces.ChoixHoraireDemandeurService;
import org.example.sim.specification.ChoixHoraireDemandeurSpecification;
import org.example.sim.exception.ResourceNotFoundException;
import org.example.sim.exception.InternalServerErrorException;
import org.example.sim.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultChoixHoraireDemandeurService implements ChoixHoraireDemandeurService {
	private final ChoixHoraireDemandeurRepository choixHoraireDemandeurRepository;

	public DefaultChoixHoraireDemandeurService(ChoixHoraireDemandeurRepository choixHoraireDemandeurRepository) {
	   this.choixHoraireDemandeurRepository = choixHoraireDemandeurRepository;
	}

	@Override
	public String exportChoixHoraireDemandeurToCSV(List<ChoixHoraireDemandeur> choixHoraireDemandeur) {
	   return ExportUtils.generateCsv(choixHoraireDemandeur);
	}  

	@Override
	public Page<ChoixHoraireDemandeur> getAllChoixHoraireDemandeur(Pageable pageable) {
	    try {
	        return choixHoraireDemandeurRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving choix horaire demandeur", ex);
	    }
	}

	@Override
	public Page<ChoixHoraireDemandeur> getAllChoixHoraireDemandeur(Pageable pageable, ChoixHoraireDemandeurSearch object) {
	    try {
	        Specification<ChoixHoraireDemandeur> spec=ChoixHoraireDemandeurSpecification.filter(object);
	        return choixHoraireDemandeurRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving choix horaire demandeur", ex);
	    }
	}

	@Override
	public ChoixHoraireDemandeur getChoixHoraireDemandeurById(Integer id) {
	    Optional<ChoixHoraireDemandeur> choixHoraireDemandeur = choixHoraireDemandeurRepository.findById(id);
	    if (choixHoraireDemandeur.isPresent()) {
	        return choixHoraireDemandeur.get();
	    } else {
	        throw new ResourceNotFoundException("ChoixHoraireDemandeur not found with id : " + id);
	    }
	}

	@Override
	public ChoixHoraireDemandeur createChoixHoraireDemandeur(ChoixHoraireDemandeur choixHoraireDemandeur) {
	    try {
	        return choixHoraireDemandeurRepository.save(choixHoraireDemandeur);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating choix horaire demandeur", ex);
	    }
	}

	@Override
	public ChoixHoraireDemandeur updateChoixHoraireDemandeur(Integer id, ChoixHoraireDemandeur choixHoraireDemandeur) {
	    Optional<ChoixHoraireDemandeur> existingChoixHoraireDemandeur = choixHoraireDemandeurRepository.findById(id);
	    if (existingChoixHoraireDemandeur.isPresent()) {
	        choixHoraireDemandeur.setId(id);
	        try {
	            return choixHoraireDemandeurRepository.save(choixHoraireDemandeur);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating choix horaire demandeur", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("ChoixHoraireDemandeur not found with id : " + id);
	    }
	}

	@Override
	public void deleteChoixHoraireDemandeur(Integer id) {
	    try {
	        choixHoraireDemandeurRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting choix horaire demandeur", ex);
	    }
	}



}
