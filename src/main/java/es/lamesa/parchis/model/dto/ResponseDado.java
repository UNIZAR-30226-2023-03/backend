package es.lamesa.parchis.model.dto;

import lombok.Data;
import java.util.List;

import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.Casilla;
import es.lamesa.parchis.model.Color;

@Data
public class ResponseDado {
    
    private List<Ficha> fichas;
    private boolean sacar;
    private Ficha comida;
    private Casilla casilla;
    private Color turno;

    public ResponseDado(List<Ficha> fichas, boolean sacar, Ficha comida, Casilla casilla, Color turno) {
        this.fichas = fichas;
        this.sacar = sacar;
        this.comida = comida;
        this.casilla = casilla;
        this.turno = turno;
    }

}
