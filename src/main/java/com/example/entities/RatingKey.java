package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingKey implements Serializable {

    @Column(name = "rating_id")
    Long idRating;

    @Column(name = "rated_id")
    Long idRated;
}
