package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class LigneCommandeFournisseurDto {
    private Integer id;
    private ArticleDto article;

    private CommandeFournisseurDto commandeFournisseur;

    private Integer idEntreprise;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;


    public static LigneCommandeFournisseurDto fromLigneCommandeFournisseur(LigneCommandeFournisseur ligneCommandeFournisseur){
        if(ligneCommandeFournisseur == null ) throw new RuntimeException("ligneCommandeFournisseur not found!!");

        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .article(ArticleDto.fromArticle( ligneCommandeFournisseur.getArticle()))
                .commandeFournisseur(CommandeFournisseurDto.fromCommandeFournisseur(ligneCommandeFournisseur.getCommandeFournisseur()))
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    public static LigneCommandeFournisseur toLigneCommandeFournisseur(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        if(ligneCommandeFournisseurDto == null ) throw new RuntimeException("ligneCommandeFournisseur not found!!");

        LigneCommandeFournisseur ligneCommandeFournisseur   = new LigneCommandeFournisseur();

        ligneCommandeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCommandeFournisseur.setArticle(ArticleDto.toArticle( ligneCommandeFournisseurDto.getArticle()));
        ligneCommandeFournisseur.setCommandeFournisseur(CommandeFournisseurDto.toCommandeFournisseur(ligneCommandeFournisseurDto.getCommandeFournisseur()));
        ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCommandeFournisseur.setIdEntreprise(ligneCommandeFournisseurDto.getIdEntreprise());
        return ligneCommandeFournisseur;
    }
}
