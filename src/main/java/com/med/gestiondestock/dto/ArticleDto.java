package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ArticleDto {
    private Integer id;
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaireHt; //hors tva
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTtc;
    private String photo;

    private CategoryDto category;

    @JsonIgnore
    private List<MvtStkDto> mvtStks;

    @JsonIgnore
    private List<LigneVenteDto> ligneVentes;

    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients;

    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    @JsonIgnore
    private EntrepriseDto entreprise;

    private Integer idEntreprise;


    public static ArticleDto fromArticle(Article article) {
        if (article == null) throw new RuntimeException("Article not found!!");

        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .tauxTva(article.getTauxTva())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .photo(article.getPhoto())
                .category(CategoryDto.fromCategory(article.getCategory()))
                //.entreprise(EntrepriseDto.fromEntity(article.getEntreprise()))
                .idEntreprise(article.getIdEntreprise())
                .build();
    }

    public static Article toArticle(ArticleDto articleDto) {
        if (articleDto == null) throw new RuntimeException("Article not found!!");

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setTauxTva(articleDto.getTauxTva());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setPhoto(articleDto.getPhoto());
        article.setIdEntreprise(articleDto.getIdEntreprise());
        article.setCategory(CategoryDto.toCategory(articleDto.getCategory()));
        //article.setEntreprise(EntrepriseDto.toEntity(articleDto.getEntreprise()));

        return article;
    }
}
