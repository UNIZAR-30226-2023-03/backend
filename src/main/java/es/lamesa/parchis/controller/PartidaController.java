package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.lamesa.parchis.service.PartidaService;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.Ficha;
import es.lamesa.parchis.model.dto.PartidaDto;
import es.lamesa.parchis.model.dto.RequestPartidaPublica;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.ResponseMovimiento;

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
    public ResponsePartida crearPartidaPrivada(@RequestBody PartidaDto request) {
        return service.crearPartidaPrivada(request);
    }
    
    @PostMapping("/conectar")
    public ResponsePartida conectarPartidaPrivada(@RequestBody PartidaDto request) {
        return service.conectarPartidaPrivada(request);
    }

    @PostMapping("/empezar/{id}")
    public void empezarPartida(@PathVariable("id") Long id) {
        service.empezarPartida(id);
    }
    
    @PostMapping("/publica")
    public ResponsePartida jugarPartidaPublica(@RequestBody RequestPartidaPublica request) {
        return service.jugarPartidaPublica(request);
    }

    @PostMapping("/dado/{id}")
    public ResponseMovimiento comprobarMovimientos(@PathVariable("id") Long id, int dado) {
        return service.comprobarMovimientos(id, dado);
    }

    @PostMapping("/movimiento/{id}")
    public void realizarMovimiento(@PathVariable("id") Long id, int id_ficha){
        service.realizarMovimiento(id, id_ficha);
    }
}
