package es.lamesa.parchis.model.dto;

import lombok.Data;
import java.util.List;

import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.Casilla;

@Data
public class ResponseMovimiento {
    
    private List<Ficha> fichas;
    private boolean sacar;
    private Ficha comida;
    private Casilla casilla;

    public ResponseMovimiento(List<Ficha> fichas, boolean sacar, Ficha comida, Casilla casilla) {
        this.fichas = fichas;
        this.sacar = sacar;
        this.comida = comida;
        this.casilla = casilla;
    }

}
