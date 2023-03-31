package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.lamesa.parchis.service.PartidaService;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.dto.PartidaDto;
import es.lamesa.parchis.model.dto.RequestPartidaPublica;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    PartidaService service;
    
    @GetMapping()
    public List<Partida> getPartidas() {
        return service.getPartidas();
    }

    @PostMapping("/crear")
    public Long crearPartidaPrivada(@RequestBody PartidaDto request) {
        return service.crearPartidaPrivada(request).getId();
    }
    
    @PostMapping("/conectar")
    public Long conectarPartidaPrivada(@RequestBody PartidaDto request) {
        return service.conectarPartidaPrivada(request).getId();
    }
    
    @PostMapping("/publica")
    public Long jugarPartidaPublica(@RequestBody RequestPartidaPublica request) {
        return service.jugarPartidaPublica(request).getId();
    }

    @PostMapping("/dado")
    public void registrarDado(){
        
    }
}
