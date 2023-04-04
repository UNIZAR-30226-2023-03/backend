package es.lamesa.parchis.model.dto;

import lombok.Data;

import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;

@Data
public class RequestPartida {
    private String nombre;
    private String password;
    private Long jugador;
    private ConfigBarreras configuracionB;
    private ConfigFichas configuracionF;
}
