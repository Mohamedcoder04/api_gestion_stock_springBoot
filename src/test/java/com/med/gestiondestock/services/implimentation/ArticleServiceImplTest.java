package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.dto.CategoryDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.services.ArticleService;
import com.med.gestiondestock.services.CategoryService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService service;
    @Autowired
    private CategoryService categoryService;

    @Test
    public void shouldSaveArticleWithSuccess(){
        CategoryDto categoryDto = categoryService.findById(15);
        ArticleDto expectedArticle = ArticleDto.builder()
                .codeArticle("Article test")
                .designation("designation test")
                .idEntreprise(1)
                .prixUnitaireTtc(BigDecimal.ONE)
                .prixUnitaireHt(BigDecimal.TEN)
                .tauxTva(BigDecimal.ONE)
                .category(categoryDto)
                .build();
        ArticleDto saveArticle = service.save(expectedArticle);

        assertNotNull(saveArticle);
        assertNotNull(saveArticle.getId());

        assertEquals(expectedArticle.getCodeArticle(), saveArticle.getCodeArticle());
        assertEquals(expectedArticle.getDesignation(), saveArticle.getDesignation());
        assertEquals(expectedArticle.getIdEntreprise(), saveArticle.getIdEntreprise());
        assertEquals(expectedArticle.getCategory(), saveArticle.getCategory());

    }



    @Test
    public void shouldInvalidEntityExveption(){
        ArticleDto articleDto = ArticleDto.builder().build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, ()-> service.save(articleDto));
        Assertions.assertEquals( ErrorCodes.ARTICLE_NOT_VALID, expectedException.getErrorCodes() );
        assertEquals(5, expectedException.getErrors().size() );
        assertEquals("Veuillez renseigner le code de l'article...", expectedException.getErrors().get(0));
    }

    @Test
    public void shouldEntityNotFoundException(){

        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, ()-> service.findById(0));
        assertEquals( ErrorCodes.ARTICLE_NOT_FOUND, expectedException.getErrorCodes() );
        assertEquals("aucun Article a l'Id 0", expectedException.getMessage());
    }

    @Test(expected = EntityNotFoundException.class) // on dit que j'attend une exception de type EntityNotFound
    public void shouldEntityNotFoundException2(){
        service.findById(0);
    }


}