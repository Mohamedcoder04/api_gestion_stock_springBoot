package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.LigneVente;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneVenteDto {
    private Integer id;
    private VenteDto vente;

    private ArticleDto article;

    private Integer idEntreprise;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;


    public static LigneVenteDto fromLigneVente(LigneVente ligneVente){
        if(ligneVente == null) throw new RuntimeException("LigneVente not found!!!");

        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .vente(VenteDto.fromVente(ligneVente.getVente()))
                .article(ArticleDto.fromArticle(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .idEntreprise(ligneVente.getIdEntreprise())
                .build();
    }

    public static LigneVente toLigneVente(LigneVenteDto ligneVenteDto){
        if(ligneVenteDto == null ) throw new RuntimeException("LigneVente not found!!!");
        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setVente(VenteDto.toVente(ligneVenteDto.getVente()));
        ligneVente.setArticle(ArticleDto.toArticle(ligneVenteDto.getArticle()));
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());

        return ligneVente;
    }
}
