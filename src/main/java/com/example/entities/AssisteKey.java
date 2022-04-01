package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class AssisteKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "trajet_id")
    Long trajetId;

}
