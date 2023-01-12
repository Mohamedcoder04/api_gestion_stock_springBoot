package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Optional<Article> findArticleByCodeArticle(String codeArticle);
    List<Article> findAllByCategoryId(Integer idCategory);

    List<Article> findAllByEntrepriseId(Integer id);
}
