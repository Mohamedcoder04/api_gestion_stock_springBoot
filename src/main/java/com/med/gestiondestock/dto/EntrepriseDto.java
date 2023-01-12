package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Entreprise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import java.util.List;

@Data
@Builder
public class EntrepriseDto {
    private Integer id;
    private String nom;
    @Embedded
    private AdresseDto adresse;
    private String codeFiscal;
    private String photo;
    private String email;
    private String numTel;
    private String siteWeb;
    private String description;

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    @JsonIgnore
    private List<ArticleDto> articles;


    public static EntrepriseDto fromEntreprise(Entreprise entreprise) {
        if (entreprise == null) throw new RuntimeException("entreprise not found!!");

        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
                .description(entreprise.getDescription())
                .build();
    }

    public static Entreprise toEntreprise(EntrepriseDto entrepriseDto) {
        if (entrepriseDto == null){
            throw new RuntimeException("Entreprise not found!!");
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
        entreprise.setDescription(entrepriseDto.getDescription());

        return entreprise;
    }
}
