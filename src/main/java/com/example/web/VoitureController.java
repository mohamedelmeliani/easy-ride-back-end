package com.example.web;

import com.example.dao.VoitureRepo;
import com.example.entities.Utilisateur;
import com.example.entities.Voiture;
import com.example.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class VoitureController {

    private VoitureRepo voitureRepo;
    private UtilisateurService service;

    @PostMapping(path="/addcar")
    public Voiture addCar(Principal user,@RequestBody Voiture voiture){
        Utilisateur utilisateur=service.loadUserByUsername(user.getName());
        voiture.setOwner(utilisateur);
        return voitureRepo.save(voiture);
    }
}
