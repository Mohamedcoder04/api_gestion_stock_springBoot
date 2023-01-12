package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Client extends  AbstractEntity {
    private String nom;
    private String prenom;
    @Column(name = "identreprise")
    private Integer idEntreprise;
    @Embedded
    private Adresse adresse;

    private String photo;
    private String email;
    private String numTel;
    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;
}
