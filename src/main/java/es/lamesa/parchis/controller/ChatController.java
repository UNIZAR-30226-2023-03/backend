package es.lamesa.parchis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import es.lamesa.parchis.model.dto.MensajeDto;

@RestController
@RequestMapping("/")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{id}")
    public void enviarMensaje(@DestinationVariable Long id, MensajeDto mensaje) {
        messagingTemplate.convertAndSend("/topic/chat/" + id, mensaje);
    }

    // @MessageMapping("/chat.addUser")
    // @SendTo("/topic/{id}")
    // public MensajeDto addUser(@Payload MensajeDto mensaje, SimpMessageHeaderAccessor headerAccessor) {
        
    //     // Add username in web socket session
    //     headerAccessor.getSessionAttributes().put("username", mensaje.getUser());
    //     return mensaje;
    // }

}
