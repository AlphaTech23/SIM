package org.example.sim.services.interfaces;

import org.example.sim.models.TypeOffre;
import org.example.sim.models.dto.TypeOffreSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface TypeOffreService {
    Page<TypeOffre> getAllTypeOffre(Pageable pageable);

    Page<TypeOffre> getAllTypeOffre(Pageable pageable, TypeOffreSearch object);

    TypeOffre getTypeOffreById(Integer id);

    public String exportTypeOffreToCSV(List<TypeOffre> typeOffre);

    

    TypeOffre createTypeOffre(TypeOffre typeOffre);

    TypeOffre updateTypeOffre(Integer id, TypeOffre typeOffre);

    void deleteTypeOffre(Integer id);
    

}
