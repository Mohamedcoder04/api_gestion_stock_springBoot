package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class LigneCommandeClientDto {
    private Integer id;
    private ArticleDto article;
    private CommandeClientDto commandeClient;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
    private Integer idEntreprise;

    public static LigneCommandeClientDto fromLigneCommandeClient(LigneCommandeClient ligneCommandeClient){
        if(ligneCommandeClient == null ) throw new RuntimeException("ligneCommandeClient not found!!");

        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDto.fromArticle( ligneCommandeClient.getArticle()))
                .commandeClient(CommandeClientDto.fromCommandeClient(ligneCommandeClient.getCommandeClient()))
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .build();
    }

    public static LigneCommandeClient toLigneCommandeClient(LigneCommandeClientDto ligneCommandeClientDto){
        if(ligneCommandeClientDto == null ) throw new RuntimeException("Fournisseur not found!!");

        LigneCommandeClient ligneCommandeClient   = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ArticleDto.toArticle( ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toCommandeClient(ligneCommandeClientDto.getCommandeClient()));
        ligneCommandeClient.setQuantite(ligneCommandeClient.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClient.getPrixUnitaire());
        ligneCommandeClient.setIdEntreprise(ligneCommandeClientDto.getIdEntreprise());
        return ligneCommandeClient;
    }
}
