package com.med.gestiondestock.services.implimentation;

import com.aghzer.gestiondestock.dto.*;
import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.med.gestiondestock.dto.LigneVenteDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.model.Article;
import com.med.gestiondestock.model.LigneCommandeClient;
import com.med.gestiondestock.model.LigneCommandeFournisseur;
import com.med.gestiondestock.model.LigneVente;
import com.med.gestiondestock.repositories.ArticleRepository;
import com.med.gestiondestock.repositories.LigneCommandeClientRepository;
import com.med.gestiondestock.repositories.LigneCommandeFournisseurRepository;
import com.med.gestiondestock.repositories.LigneVenteRepository;
import com.med.gestiondestock.services.ArticleService;
import com.med.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, LigneCommandeClientRepository ligneCommandeClientRepository, LigneVenteRepository ligneVenteRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository) {
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        List<String> erros = ArticleValidator.validate(articleDto);
        if (!erros.isEmpty()) {
            log.error("Article not valid {}", articleDto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, erros);
        }
        // Article articleSave = articleRepository.save(ArticleDto.toArticle(articleDto));

        return ArticleDto.fromArticle(/*articleSave ou bien --> */
                articleRepository.save(
                        ArticleDto.toArticle(articleDto)
                )
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        // la méthode de el yousfi ou bien

        if (id == null) {
            log.error("Article Id is null");
            return null;
        }
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("aucun Article a l'Id " + id, ErrorCodes.ARTICLE_NOT_FOUND));
        ArticleDto articleDto = ArticleDto.fromArticle(article);

        return articleDto;
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (!StringUtils.hasLength(codeArticle)) {
            log.error("codeArticle is null");
            return null;
        }
        Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);
        ArticleDto articleDto = ArticleDto.fromArticle(article.get());

        return Optional.of(articleDto).orElseThrow(() -> new EntityNotFoundException("aucun Article a ce code " + codeArticle, ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {

        return articleRepository.findAll().stream()
                .map(ArticleDto::fromArticle)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Article Id is null");
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déja utilisé dans des commande Client",ErrorCodes.ARTICLE_ALREADY_USE);
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déja utilisé dans des commande fournisseur",ErrorCodes.ARTICLE_ALREADY_USE);
        }

        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déja utilisé dans des vente",ErrorCodes.ARTICLE_ALREADY_USE);
        }
        articleRepository.deleteById(id);
    }





    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        checkIdArticle(idArticle);
        return ligneCommandeClientRepository.findAllByCommandeClientId(idArticle)
                .stream()
                .map(LigneCommandeClientDto::fromLigneCommandeClient)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        checkIdArticle(idArticle);
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(idArticle);

        return ligneVentes.stream()
                .map(LigneVenteDto::fromLigneVente)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        checkIdArticle(idArticle);
        return ligneCommandeFournisseurRepository.findAllByArticleId(idArticle)
                .stream()
                .map(LigneCommandeFournisseurDto::fromLigneCommandeFournisseur)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByCategory(Integer idCategory) {
        checkIdCategory(idCategory);

        return articleRepository.findAllByCategoryId(idCategory)
                .stream()
                .map(ArticleDto::fromArticle)
                .collect(Collectors.toList());
    }

    private void checkIdArticle(Integer id){
        if(id == null){
            throw new EntityNotFoundException("aucun article avec l'ID"+id+" n'a été trouvé",ErrorCodes.ARTICLE_NOT_FOUND);
        }
    }
    private void checkIdCategory(Integer id){
        if(id == null){
            throw new EntityNotFoundException("aucun article avec l'ID"+id+" n'a été trouvé",ErrorCodes.CATEGORY_NOT_FOUND);
        }
    }
}
