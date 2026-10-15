package org.example.sim.models.dto;

import org.example.sim.models.Statut;

import org.example.sim.models.StatutRendezVou;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatutFullDto {
    private Statut statut;
    
    private List<StatutRendezVou> statutRendezVous;
    
}
