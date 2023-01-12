package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class UtilisateurDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Instant dateDeNaissance;

    @Embedded
    private AdresseDto adresse;
    private String photo;

    private EntrepriseDto entreprise;

    private List<RoleDto> roles;

    public static UtilisateurDto fromUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) throw new RuntimeException("Utilisateur not found!!!");

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .email(utilisateur.getEmail())
                .motDePasse(utilisateur.getMoteDePasse())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntreprise(utilisateur.getEntreprise()))
                /*.roles(
                        utilisateur.getRoles() != null ?
                        utilisateur.getRoles().stream()
                                .map(RoleDto::fromRole)
                                .collect(Collectors.toList()) : null
                )*/
                .build();
    }

    public static Utilisateur toUtilisateur(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) throw new RuntimeException("Utilisateur not found!!!");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setMoteDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setPhoto(utilisateurDto.getPrenom());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntreprise(utilisateurDto.getEntreprise()));

        return utilisateur;
    }
}
