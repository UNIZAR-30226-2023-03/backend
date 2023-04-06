package es.lamesa.parchis.model.dto;

import java.util.List;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.dto.UsuarioColorDto;

@Data
public class ResponsePartida {
    
    @Schema(description = "Id de la partida a la que se ha unido el jugador")
    private Long id;
    @Schema(description = "Color asignado al jugador")
    private Color color;
    @Schema(description = "Jugadores unidos a la partida y su color")
    private List<UsuarioColorDto> jugadores;

    public ResponsePartida(Long id, Color color, List<UsuarioColorDto> jugadores) {
        this.id = id;
        this.color = color;
        this.jugadores = jugadores;
    }

}
