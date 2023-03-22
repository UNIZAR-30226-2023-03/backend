package es.lamesa.parchis.model.dto;

import lombok.Data;

@Data
public class RequestAmistad {
    private Long usuario;
    private Long amigo;
}
