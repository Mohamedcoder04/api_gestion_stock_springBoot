package com.med.gestiondestock.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Adresse implements Serializable {
    private String adresse1;
    private String adresse2;
    private String ville;
    private String codePostale;
    private String pays;
}
