package com.med.gestiondestock.services;

import com.aghzer.gestiondestock.dto.*;
import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.med.gestiondestock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto articleDto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    void delete(Integer id);

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);
    List<ArticleDto> findAllArticleByCategory(Integer idCategory);

}
