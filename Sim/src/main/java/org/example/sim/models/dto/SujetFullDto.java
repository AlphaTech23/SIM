package org.example.sim.models.dto;

import org.example.sim.models.Sujet;

import org.example.sim.models.SujetsMembre;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SujetFullDto {
    private Sujet sujet;
    
    private List<SujetsMembre> sujetsMembres;
    
}
