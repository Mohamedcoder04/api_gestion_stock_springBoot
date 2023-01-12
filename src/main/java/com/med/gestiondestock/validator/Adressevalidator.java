package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.AdresseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Adressevalidator {
    public static List<String> validate(AdresseDto adresseDto){
        List<String> errors = new ArrayList<>();
        if(adresseDto == null){
            errors.add("Veuillez renseigné les données de l'adresse");
            return errors;
        }
        if(!StringUtils.hasLength(adresseDto.getAdresse1())){
            errors.add("l'adresse1 est obligatoire");
        }
        if(!StringUtils.hasLength(adresseDto.getVille())){
            errors.add("la ville est obligatoire");
        }
        if(!StringUtils.hasLength(adresseDto.getPays())){
            errors.add("le pays est obligatoire");
        }
        if(!StringUtils.hasLength(adresseDto.getCodePostale())){
            errors.add("le code postale est obligatoire");
        }
        return errors;
    }
}
