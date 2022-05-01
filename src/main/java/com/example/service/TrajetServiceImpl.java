package com.example.service;

import com.example.dao.TrajetRepo;
import com.example.entities.Trajet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class TrajetServiceImpl implements TrajetService{
    private TrajetRepo trajetRepo;

    @Override
    public Trajet trajetParVilleDetVilleAetdateD(String villeD, String villeA, LocalDate dateD) {
        return trajetRepo.findByVilleDAndVilleAAndDate(villeD,villeA,dateD);
    }
}
