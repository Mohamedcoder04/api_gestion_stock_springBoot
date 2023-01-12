package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Adresse;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AdresseDto {
    private String adresse1;
    private String adresse2;
    private String ville;
    private String codePostale;
    private String pays;

    public static AdresseDto fromEntity(Adresse adresse){
        if(adresse == null) throw  new RuntimeException("Adresse not found::");

        return AdresseDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .ville(adresse.getVille())
                .codePostale(adresse.getCodePostale())
                .pays(adresse.getPays())
                .build();
    }

    public static Adresse toEntity(AdresseDto adresseDto){
        if(adresseDto == null) throw  new RuntimeException("Adresse not found::");

        Adresse adresse = new Adresse();

        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setVille(adresseDto.getVille());
        adresse.setPays(adresseDto.getPays());
        adresse.setCodePostale(adresseDto.getCodePostale());

        return adresse;
    }
}
