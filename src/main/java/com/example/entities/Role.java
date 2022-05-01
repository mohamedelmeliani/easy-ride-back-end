package com.example.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor
public class Role implements Serializable {
    @Id  @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
}
