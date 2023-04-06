package es.lamesa.parchis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import es.lamesa.parchis.repository.TorneoRepository;
import es.lamesa.parchis.model.Torneo;

@Service
public class TorneoService {
    
    @Autowired
    TorneoRepository tRepository;
    
    @Autowired
    SimpMessagingTemplate messagingTemplate;
     
    public List<Torneo> getTorneos() {
        return tRepository.findAll();
    }
    
    // public void crearTorneo() {}
}
