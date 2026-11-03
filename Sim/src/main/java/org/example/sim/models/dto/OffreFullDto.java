package org.example.sim.models.dto;

import org.example.sim.models.Offre;

import org.example.sim.models.CiblesOffre;
import org.example.sim.models.HistoOffre;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OffreFullDto {
    private Offre offre;
    
    private List<CiblesOffre> ciblesOffres;
    private List<HistoOffre> histoOffres;
    
}
