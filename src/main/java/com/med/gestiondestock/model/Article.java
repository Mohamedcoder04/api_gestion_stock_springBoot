package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Article extends AbstractEntity {
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaireHt; //hors tva
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTtc;
    private String photo;
    @Column(name = "identreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<MvtStk> mvtStks;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    @ManyToOne
    @JoinColumn(name = "entreprise")
    private Entreprise entreprise;

}
