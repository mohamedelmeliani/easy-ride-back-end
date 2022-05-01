package com.example.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class Utilisateur implements Serializable {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String nom;
    @NonNull
    private LocalDate date_naissance;
    @NonNull
    private String adresse;
    @NonNull
    private String tel;
    @NonNull
    private String numPermis;
    @NonNull
    private String catPermis;
    @NonNull
    @Column(nullable = false,unique = true)
    private String email;
    @NonNull
    @Column(nullable = false)
    private String password;
    @Lob
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profilePic", referencedColumnName = "id")
    @JsonIgnoreProperties({"fileName", "fileType","data"})
    private Attachment photoProfile;
    private final LocalDate dateCmpt = LocalDate.now();
    private String token = UUID.randomUUID().toString();
    private boolean enabled = false;
    @NonNull
    private boolean isActive;
    private boolean isVerified;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> Roles=new ArrayList<>();
}
