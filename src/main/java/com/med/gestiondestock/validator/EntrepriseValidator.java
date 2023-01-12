package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {
    public static List<String> validate(EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();
        if(entrepriseDto == null){
            errors.add("Veuillez renseigner les données de l'entreprise...");
            return errors;
        }
        // on vérifier est ce que le code est vide
        //parceque il est obligé de remplir le code de l'entreprise
        // !StringUtils.hasLength(categoryDto.getCodeCategory()) est vide

        if(!StringUtils.hasLength(entrepriseDto.getNom())){
            errors.add("Veuillez renseigner le nom de l'entreprise...");
        }

        if(!StringUtils.hasLength(entrepriseDto.getEmail())){
            errors.add("Veuillez renseigner l'Email de l'entreprise...");
        }

        errors.addAll(Adressevalidator.validate(entrepriseDto.getAdresse()));

        if(!StringUtils.hasLength(entrepriseDto.getCodeFiscal())){
            errors.add("Veuillez renseigner le CodeFiscal de l'entreprise...");
        }
        return errors;
    }
}
