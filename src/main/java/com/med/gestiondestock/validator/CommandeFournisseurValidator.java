package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto commandeFournisseurDto){
        List<String> errors = new ArrayList<>();
        if(commandeFournisseurDto == null){
            errors.add("Veuillez renseigné les données de la commande client");
            return errors;
        }
        if(!StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("Veuillez renseigné le code de la commande client");
        }
        if(commandeFournisseurDto.getDateCommande() == null){
            errors.add("Veuillez renseigné le référence de la commande client");
        }
        if(commandeFournisseurDto.getFournisseur() == null || commandeFournisseurDto.getFournisseur().getId() == null){
            errors.add("Veuillez renseigné le fournisseur de la commande client");
        }
        return errors;
    }
}
