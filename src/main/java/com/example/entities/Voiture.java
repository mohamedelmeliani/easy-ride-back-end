package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Voiture implements Serializable {
    @Id
    private String mat;
    private String annee;
    private String cat;
    private String color;
    private int nbrePlace;
    private boolean isAC;
    private boolean isSmock;
    private boolean isMusic;
    private boolean isBabies;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_owner")
    private Utilisateur owner;
}
