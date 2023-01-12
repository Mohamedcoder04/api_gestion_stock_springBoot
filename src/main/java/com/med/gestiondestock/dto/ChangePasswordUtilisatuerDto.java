package com.med.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class ChangePasswordUtilisatuerDto {
    private Integer id;
    private String motDePasse;
    private String confirmMotDePasse;
}
