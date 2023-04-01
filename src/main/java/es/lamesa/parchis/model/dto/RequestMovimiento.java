package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class RequestMovimiento {

    private Long partida;
    private int ficha;
    private int dado;

}
