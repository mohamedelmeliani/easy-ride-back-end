package com.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor
public class Trajet implements Serializable {
    @Id @GeneratedValue
    private long id;
    @NonNull
    private String villeD;
    @NonNull
    private String villeA;
    @NonNull
    private Date date;
    @NonNull
    private String heure;
    @NonNull
    private int nbrePlace;
    @NonNull
    private double prix;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "used_car")
    private Voiture used_car;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Utilisateur driver;
    @OneToMany(mappedBy = "trajet")
    private Collection<Assiste> assistes;
}
