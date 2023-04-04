package es.lamesa.parchis.model.dto;

import es.lamesa.parchis.model.ConfigBarreras;
import es.lamesa.parchis.model.ConfigFichas;
import lombok.Data;

@Data
public class RequestPartidaPublica {
    private Long jugador;
    private ConfigBarreras configuracionB;
    private ConfigFichas configuracionF;
}

