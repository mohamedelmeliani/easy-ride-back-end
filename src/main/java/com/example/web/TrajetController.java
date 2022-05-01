package com.example.web;

import com.example.entities.Trajet;
import com.example.service.TrajetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class TrajetController {
    @Autowired
    private TrajetService trajetService;

    @GetMapping(path = "/gettrajet")
    public Trajet get_trajet(@RequestBody trajetSearchForum form){
        return trajetService.trajetParVilleDetVilleAetdateD(form.getVilleD(),form.getVilleA(),form.getDate());
    }

}

@Data
class trajetSearchForum{
    private String villeA;
    private String villeD;
    private LocalDate date;
}
