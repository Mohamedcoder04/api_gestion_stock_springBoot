package com.med.gestiondestock.services.strategy.impl;

import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.services.ArticleService;
import com.med.gestiondestock.services.FlickService;
import com.med.gestiondestock.services.strategy.Strategy;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {
    private FlickService flickService;
    private ArticleService articleService;

    @Autowired
    public SaveArticlePhoto(FlickService flickService, ArticleService articleService) {
        this.flickService = flickService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDto articleDto = articleService.findById(id);
        String urlPhoto = flickService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDto.setPhoto(urlPhoto);
        return articleService.save(articleDto);
    }
}
