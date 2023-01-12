package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class LigneVente extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idVente")
    private Vente vente;
    @Column(name = "identreprise")
    private Integer idEntreprise;
    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;

    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
}
