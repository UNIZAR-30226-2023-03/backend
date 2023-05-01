package es.lamesa.parchis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

import es.lamesa.parchis.service.PartidaService;
import es.lamesa.parchis.model.Color;
import es.lamesa.parchis.model.Partida;
import es.lamesa.parchis.model.dto.RequestPartida;
import es.lamesa.parchis.model.dto.RequestConexion;
import es.lamesa.parchis.model.dto.RequestPartidaAmigo;
import es.lamesa.parchis.model.dto.RequestPartidaPublica;
import es.lamesa.parchis.model.dto.RequestSalir;
import es.lamesa.parchis.model.dto.ResponsePartida;
import es.lamesa.parchis.model.dto.ResponseDado;
import es.lamesa.parchis.model.dto.ResponseMovimiento;
import es.lamesa.parchis.model.dto.RequestMovimiento;

@RestController
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    PartidaService service;
    
    @GetMapping()
    @Operation(summary = "Obtiene todas las partidas creadas")
    public List<Partida> getPartidas() {
        return service.getPartidas();
    }

    @PostMapping("/crear")
    @Operation(summary = "Crea una nueva partida privada")
    public ResponsePartida crearPartidaPrivada(@RequestBody RequestPartida request) {
        return service.crearPartidaPrivada(request);
    }
    
    @PostMapping("/conectar")
    @Operation(summary = "Conecta con una partida privada existente")
    public ResponsePartida conectarPartidaPrivada(@RequestBody RequestConexion request) {
        return service.conectarPartidaPrivada(request);
    }

    @PostMapping("/conectar-amigo")
    @Operation(summary = "Conecta con una partida privada existente de un amigo")
    public ResponsePartida conectarPartidaAmigo(@RequestBody RequestPartidaAmigo request) {
        return service.conectarPartidaAmigo(request);
    }

    @PostMapping("/empezar/{id}")
    @Operation(summary = "Inicia una partida existente, debe haber conectados al menos 2 jugadores")
    public Color empezarPartida(@PathVariable("id") Long id) {
        return service.empezarPartida(id);
    }
    
    @PostMapping("/publica")
    @Operation(summary = "Conecta con una partida pública. Si no existe, la crea")
    public ResponsePartida jugarPartidaPublica(@RequestBody RequestPartidaPublica request) {
        return service.jugarPartidaPublica(request);
    }

    @PostMapping("/dado/{id}")
    @Operation(summary = "Comprueba los movimientos posibles dada una tirada")
    public ResponseDado comprobarMovimientos(@PathVariable("id") Long id, int dado) {
        return service.comprobarMovimientos(id, dado);
    }

    @PostMapping("/movimiento")
    @Operation(summary = "Mueve una ficha en función del dado obtenido")
    public ResponseMovimiento realizarMovimiento(@RequestBody RequestMovimiento request) {
        return service.realizarMovimiento(request);
    }
    
    @PostMapping("/pausa/{id}")
    @Operation(summary = "El juego se pausa cuando un jugador pulsa el botón")
    public boolean pausarPartida(@PathVariable("id") Long id) {
        return service.pausarPartida(id);
    }

    @PostMapping("/salir")
    @Operation(summary = "El usuario sale de la partida")
    public boolean salirPartida(@RequestBody RequestSalir request) {
        return service.salirPartida(request);
    }

    @PostMapping("/reconectar/{id}")
    @Operation(summary = "El usuario sale de la partida")
    public ResponsePartida reconectarPartida(@PathVariable("id") Long id) {
        return service.reconectarPartida(id);
    }
}
