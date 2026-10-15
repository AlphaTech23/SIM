package org.example.sim.models.dto;

import org.example.sim.models.Secteur;

import org.example.sim.models.Membre;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecteurFullDto {
    private Secteur secteur;
    
    private List<Membre> membres;
    
}
