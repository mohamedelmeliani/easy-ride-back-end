package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Assiste implements Serializable {
    @EmbeddedId
    private AssisteKey key;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @MapsId("trajetId")
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;
    private String status;
    private double stars;
}
