package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import es.lamesa.parchis.model.Torneo;
import es.lamesa.parchis.service.TorneoService;
import es.lamesa.parchis.model.dto.RequestTorneo;
import es.lamesa.parchis.model.dto.ResponseTorneo;

@RestController
@RequestMapping("/torneo")
public class TorneoController {

    @Autowired
    TorneoService service;

    @GetMapping()
    @Operation(summary = "Obtiene todos los torneos creados")
    public List<Torneo> getTorneos() {
        return service.getTorneos();
    }

    @PostMapping("/apuntar")
    @Operation(summary = "Un usuario se apunta a un torneo")
    public ResponseTorneo apuntarATorneo(@RequestBody RequestTorneo rt) {
        return service.apuntarATorneo(rt);
    }
    
}
