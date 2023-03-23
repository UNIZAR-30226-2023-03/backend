package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class RequestRespuestaAmistad {
    private Long amigo;
    private boolean aceptado;
}
