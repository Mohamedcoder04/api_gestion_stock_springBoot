package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.MvtStkDto;
import com.med.gestiondestock.model.TypeMvt;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {

    public static List<String> validate(MvtStkDto mvtStkDto){
        List<String> errors = new ArrayList<>();

        // on vérifier est ce que le code est vide
        //parceque il est obligé de remplir le code de la catégorie
        // !StringUtils.hasLength(categoryDto.getCodeCategory()) est vide
        if(mvtStkDto == null){
            errors.add("Veuillez renseigner le mouvement du stock...");
        }
        if(mvtStkDto.getQuantite() == null || mvtStkDto.getQuantite().compareTo(BigDecimal.ZERO) == 0){
            errors.add("Veuillez renseigné la quantité de mouvement du stock");
        }
        if(mvtStkDto.getDateMvt() == null){
            errors.add("Veuillez renseigné la date de mouvement du stock");
        }
        if(mvtStkDto.getArticle() == null){
            errors.add("Veuillez renseigné l'article de mouvement du stock");
        }
        if(!StringUtils.hasLength(String.valueOf(mvtStkDto.getTypeMvt())) && !(mvtStkDto.getTypeMvt() instanceof TypeMvt)){
            errors.add("Veuillez renseigné l'article de mouvement du stock");
        }
        return errors;
    }
}
