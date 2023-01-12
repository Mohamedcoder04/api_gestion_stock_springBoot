package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Vente extends AbstractEntity {

    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVentes ;
    @Column(name = "identreprise")
    private Integer idEntreprise;
    private String code;
    private Instant dateVente;
    private String commentaire;
}
