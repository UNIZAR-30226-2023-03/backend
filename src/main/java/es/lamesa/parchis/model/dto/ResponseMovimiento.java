package es.lamesa.parchis.model.dto;

import lombok.Data;
import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.Casilla;
import es.lamesa.parchis.model.Color;

@Data
public class ResponseMovimiento {

    private Casilla destino;
    private Ficha comida;
    private Color turno;

    public ResponseMovimiento(Casilla destino, Ficha comida, Color turno) {
        this.destino = destino;
        this.comida = comida;
        this.turno = turno;
    }

}
