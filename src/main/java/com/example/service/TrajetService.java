package com.example.service;

import com.example.entities.Trajet;

import java.time.LocalDate;

public interface TrajetService {
    Trajet trajetParVilleDetVilleAetdateD(String villeD, String villeA, LocalDate dateD);
}
