package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import es.lamesa.parchis.model.dto.TorneoDto;
import es.lamesa.parchis.service.TorneoService;
import es.lamesa.parchis.model.dto.RequestTorneo;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.ResponseTorneo;
import es.lamesa.parchis.model.dto.RequestTorneoCrear;

@RestController
@RequestMapping("/torneo")
public class TorneoController {

    @Autowired
    TorneoService service;

    @GetMapping()
    @Operation(summary = "Obtiene todos los torneos creados")
    public List<TorneoDto> getTorneos() {
        return service.getTorneos();
    }

    @PostMapping("/crear")
    @Operation(summary = "Un usuario crea un torneo")
    public ResponseTorneo crearTorneo(@RequestBody RequestTorneoCrear rt) {
        return service.crearTorneo(rt);
    }

    @PostMapping("/apuntar")
    @Operation(summary = "Un usuario se apunta a un torneo")
    public ResponseTorneo apuntarATorneo(@RequestBody RequestTorneo rt) {
        return service.apuntarATorneo(rt);
    }

    @PostMapping("/jugar")
    @Operation(summary = "Un usuario se une a una partida libre del torneo")
    public ResponsePartida jugarTorneo(@RequestBody RequestTorneo rt) {
        return service.jugarTorneo(rt);
    }

    @PostMapping("/jugar-final")
    @Operation(summary = "Un usuario se une a la final de un torneo después de ganar su eliminatoria")
    public ResponsePartida jugarFinal(@RequestBody RequestTorneo rt) {
        return service.jugarFinal(rt);
    }
    
    @PostMapping("/desapuntar")
    @Operation(summary = "Un usuario se desapunta de un torneo")
    public void desapuntarDeTorneo(@RequestBody RequestTorneo rt) {
        service.desapuntarDeTorneo(rt);
    }

}
