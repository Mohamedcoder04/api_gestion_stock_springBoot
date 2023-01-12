package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Entreprise extends AbstractEntity {
    private String nom;
    @Embedded
    private Adresse adresse;
    private String codeFiscal;
    private String photo;
    private String email;
    private String numTel;
    private String siteWeb;
    private String description;

    @OneToMany(mappedBy = "entreprise")
    @Column(nullable = true)
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "entreprise")
    @Column(nullable = true)
    private List<Article> articles;

}
