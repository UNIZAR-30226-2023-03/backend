package es.lamesa.parchis.model.dto;

import es.lamesa.parchis.model.UsuarioPartida;
import es.lamesa.parchis.model.Partida;

import lombok.Data;

@Data
public class PartidaDto {

    private Long id;
    private UsuarioPartida jugador;
    private Partida.ConfigBarreras configuracion;

}
