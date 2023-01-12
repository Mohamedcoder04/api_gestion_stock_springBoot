package com.med.gestiondestock.validator;

import com.med.gestiondestock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto commandeClientDto){
        List<String> errors = new ArrayList<>();

        if(commandeClientDto == null){
            errors.add("Veuillez renseigné les données de la commande client");
            return errors;
        }
        if(!StringUtils.hasLength(commandeClientDto.getReference())){
            errors.add("Veuillez renseigné le référence de la commande client");
        }
        if(commandeClientDto.getDateCommande() == null){
            errors.add("Veuillez renseigné le référence de la commande client");
        }

        if(!StringUtils.hasLength(commandeClientDto.getEtatCommande().toString())){
            errors.add("Veuillez renseigné l'état de la commande client");
        }

        if(commandeClientDto.getClient() == null || commandeClientDto.getClient().getId() == null){
            errors.add("Veuillez renseigné le client de la commande client");
        }
        return errors;
    }
}
