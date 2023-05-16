package es.lamesa.parchis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import io.swagger.v3.oas.annotations.Operation;

import es.lamesa.parchis.model.dto.MensajeDto;

@RestController
@RequestMapping("/")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{id}")
    @Operation(summary = "Envía un mensaje al chat de la partida con el id dado")
    public void enviarMensaje(@DestinationVariable Long id, MensajeDto mensaje) {
        messagingTemplate.convertAndSend("/topic/chat/" + id, mensaje);
    }

}
