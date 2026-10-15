package org.example.sim.models.dto;

import org.example.sim.models.Membre;

import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.RendezVou;
import org.example.sim.models.SujetsMembre;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MembreFullDto {
    private Membre membre;
    
    private List<ActivitesMembre> activitesMembres;
    private List<RendezVou> rendezVous;
    private List<SujetsMembre> sujetsMembres;
    
}
