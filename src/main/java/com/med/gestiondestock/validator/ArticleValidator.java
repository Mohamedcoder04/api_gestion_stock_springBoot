package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto articleDto){
        List<String> errors = new ArrayList<>();

        if(articleDto == null){
            errors.add("Veuillez renseigner le données de l'article...");
            return errors;
        }

        // on vérifier est ce que le code est vide
        //parceque il est obligé de remplir le code de la catégorie
        // !StringUtils.hasLength(categoryDto.getCodeCategory()) est vide

        if(!StringUtils.hasLength(articleDto.getCodeArticle())){
            errors.add("Veuillez renseigner le code de l'article...");
        }
        if(articleDto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prixUnitaireHt de l'article...");
        }
        if(articleDto.getPrixUnitaireTtc() == null){
            errors.add("Veuillez renseigner le prixUnitaireTtc de l'article...");
        }

        if(articleDto.getTauxTva() == null){
            errors.add("Veuillez renseigner le tauxTva de l'article...");
        }
        if(articleDto.getCategory() == null){
            errors.add("Veuillez sélétionner une catégorie...");
        }
        return errors;
    }
}
