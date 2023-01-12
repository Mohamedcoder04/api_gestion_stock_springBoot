package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.CommandeFournisseur;
import com.med.gestiondestock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data @Builder
public class CommandeFournisseurDto {
    private Integer id;
    private String code;
    private Instant dateCommande;
    private FournisseurDto fournisseur;
    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;
    private Integer idEntreprise;

    private EtatCommande etatCommande;

    public static CommandeFournisseurDto fromCommandeFournisseur(CommandeFournisseur commandeFournisseur){
        if(commandeFournisseur == null) throw new RuntimeException("commandeFournisseur not found!!");

        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .fournisseur(FournisseurDto.fromFournisseur(commandeFournisseur.getFournisseur()))
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .build();
    }

    public static CommandeFournisseur toCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto){
        if(commandeFournisseurDto == null) throw new RuntimeException("commandeFournisseur not found!!");

        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setFournisseur(FournisseurDto.toFournisseur(commandeFournisseurDto.getFournisseur()));
        commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
