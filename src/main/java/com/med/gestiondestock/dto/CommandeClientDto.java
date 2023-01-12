package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.CommandeClient;
import com.med.gestiondestock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data @Builder
public class CommandeClientDto {
    private Integer id;
    private String reference;
    private Instant dateCommande;

    private EtatCommande etatCommande;

    private ClientDto client;

    private Integer idEntreprise;
    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients;

    public static CommandeClientDto fromCommandeClient(CommandeClient commandeClient){
        if(commandeClient == null) throw new RuntimeException("CommandeClient not found!!");

        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .reference(commandeClient.getReference())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDto.fromClient(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                .build();
    }

    public static CommandeClient toCommandeClient(CommandeClientDto commandeClientDto){
        if(commandeClientDto == null) throw new RuntimeException("CommandeClient not found!!");

        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setReference(commandeClientDto.getReference());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setClient(ClientDto.toClient(commandeClientDto.getClient()));
        commandeClient.setEtatCommande(commandeClient.getEtatCommande());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        return commandeClient;
    }

    public boolean isCommandeLivree(){
        return etatCommande.LIVREE.equals(this.etatCommande);
    }
}
