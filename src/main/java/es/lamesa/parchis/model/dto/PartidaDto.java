package es.lamesa.parchis.model.dto;

import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.ConfigBarreras;

import lombok.Data;

@Data
public class PartidaDto {

    private String nombre;
    private UsuarioPartida jugador;
    private ConfigBarreras configuracion;

}
