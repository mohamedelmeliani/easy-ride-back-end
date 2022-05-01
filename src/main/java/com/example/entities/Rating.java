package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {
    @EmbeddedId
    private RatingKey key;
    @ManyToOne
    @MapsId("idRating")
    @JoinColumn(name = "rating_id")
    private Utilisateur rating;

    @ManyToOne
    @MapsId("idRated")
    @JoinColumn(name = "rated_id")
    private Utilisateur rated;

    private int ratingStars;
}
