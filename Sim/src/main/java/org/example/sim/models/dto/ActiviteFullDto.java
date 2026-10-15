package org.example.sim.models.dto;

import org.example.sim.models.Activite;

import org.example.sim.models.ActivitesMembre;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActiviteFullDto {
    private Activite activite;
    
    private List<ActivitesMembre> activitesMembres;
    
}
