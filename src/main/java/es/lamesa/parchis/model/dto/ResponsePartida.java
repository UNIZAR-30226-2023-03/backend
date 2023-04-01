package es.lamesa.parchis.model.dto;

import lombok.Data;

import es.lamesa.parchis.model.Color;

@Data
public class ResponsePartida {
    
    private Long id_partida;
    private Color color_jugador;

    public ResponsePartida(Long id_partida, Color color_jugador) {
        this.id_partida = id_partida;
        this.color_jugador = color_jugador;
    }

}
