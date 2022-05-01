package com.example.dao;

import com.example.entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@RepositoryRestResource
@CrossOrigin("*")
public interface TrajetRepo extends JpaRepository<Trajet,Long> {

    Trajet findByVilleDAndVilleAAndDate(String villeD, String villeA, LocalDate date);
}
