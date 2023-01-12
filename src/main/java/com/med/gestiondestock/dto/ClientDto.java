package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.List;

@Data @Builder
public class ClientDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String photo;
    private String email;
    private String numTel;
    private Integer idEntreprise;
    @Embedded
    private AdresseDto adresse;
    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    public static ClientDto fromClient(Client client){
        if(client == null) throw new RuntimeException("Client not found!!");

        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .photo(client.getPhoto())
                .email(client.getEmail())
                .numTel(client.getNumTel())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .idEntreprise(client.getIdEntreprise())
                .build();
    }

    public static Client toClient(ClientDto clientDto){
        if(clientDto == null) throw new RuntimeException("Client not found!!");

        Client client = new Client();

        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setPhoto(clientDto.getPhoto());
        client.setEmail(clientDto.getEmail());
        client.setNumTel(clientDto.getNumTel());
        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        client.setIdEntreprise(clientDto.getIdEntreprise());

        return client;
    }
}
