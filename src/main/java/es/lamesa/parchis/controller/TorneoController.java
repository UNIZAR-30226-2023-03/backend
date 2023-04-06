package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import es.lamesa.parchis.model.Torneo;
import es.lamesa.parchis.service.TorneoService;

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

    // @PostMapping("/crear")
    // @Operation(summary = "Crea un nuevo torneo")
    // public void crearTorneo(@RequestBody RequestUsuario usuario) {
    //     service.crearTorneo();
    // }
    
}
