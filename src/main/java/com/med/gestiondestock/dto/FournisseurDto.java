package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.List;

@Data @Builder
public class FournisseurDto {
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
    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromFournisseur(Fournisseur fournisseur){
        if(fournisseur == null ) throw new RuntimeException("Fournisseur not found!!");

        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .photo(fournisseur.getPhoto())
                .email(fournisseur.getEmail())
                .numTel(fournisseur.getNumTel())
                .idEntreprise(fournisseur.getIdEntreprise())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .build();
    }

    public static Fournisseur toFournisseur(FournisseurDto fournisseurDto){
        if(fournisseurDto == null ) throw new RuntimeException("Fournisseur not found!!");
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setEmail(fournisseurDto.getEmail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setIdEntreprise(fournisseurDto.getIdEntreprise());

        return fournisseur;
    }
}
