package com.example.entities;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class Utilisateur implements Serializable {
    @Id @GeneratedValue
    private long id;
    @NonNull
    private String nom;
    @NonNull
    private Date date_naissance;
    @NonNull
    private String adresse;
    @NonNull
    private String tel;
    @NonNull
    private String numPermis;
    @NonNull
    private String catPermis;
    @NonNull
    @Column(nullable = false)
    private String email;
    @NonNull
    @Column(nullable = false)
    private String password;
    @NonNull
    private byte[] profilePic;
    private final LocalDate dateCmpt = LocalDate.now();
    @NonNull
    private boolean isActive;
    private boolean isVerified;
    @OneToMany(mappedBy = "utilisateur")
    private Collection<Assiste> assistes;
}
