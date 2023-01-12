package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.EntrepriseDto;
import com.med.gestiondestock.dto.RoleDto;
import com.med.gestiondestock.dto.UtilisateurDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.model.Article;
import com.med.gestiondestock.model.Entreprise;
import com.med.gestiondestock.model.Utilisateur;
import com.med.gestiondestock.repositories.ArticleRepository;
import com.med.gestiondestock.repositories.EntrepriseRepository;
import com.med.gestiondestock.repositories.RoleRepository;
import com.med.gestiondestock.repositories.UtilisateurRepository;
import com.med.gestiondestock.services.EntrepriseService;
import com.med.gestiondestock.services.UtilisateurService;
import com.med.gestiondestock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private UtilisateurRepository utilisateurRepository;
    private RoleRepository roleRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService, UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, ArticleRepository articleRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
        List<String> errors = EntrepriseValidator.validate(entrepriseDto);
        if(!errors.isEmpty()){
            log.error("Entreprise not Valid {}",entrepriseDto);
            throw new InvalidEntityException("entreprise n'est pas valid" , ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        Entreprise entreprise = EntrepriseDto.toEntreprise(entrepriseDto);
        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);
        EntrepriseDto dto = EntrepriseDto.fromEntreprise(savedEntreprise);

        UtilisateurDto utilisateur = fromEntreprise(dto);
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);
        RoleDto roles = RoleDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();
        roleRepository.save(RoleDto.toRole(roles));

        return dto;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto dto){
        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .email(dto.getEmail())
                .motDePasse(generateRandomPassword())
                .entreprise(dto)
                .dateDeNaissance(Instant.now())
                .photo(dto.getPhoto())
                .build();
    }

    private String generateRandomPassword() {
        return "$2y$13$llrqHBEE/aCFFY8dLyB7c.e/sMlJnPRtHqkmlJAfgkB9AMs2Z/f6O";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id == null){
            log.error("id entreprise is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        EntrepriseDto dto = EntrepriseDto.fromEntreprise(entreprise.get());
        return Optional.of(dto).orElseThrow(
                ()-> new EntityNotFoundException("aucune entreprise n'a l'id "+id, ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntreprise)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("id entreprise is null");
        }
        List<Utilisateur> utilisateurs = utilisateurRepository.findAllByEntrepriseId(id);
        if(!utilisateurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une entreprise déja utilisé dans des utilisateur", ErrorCodes.ENTREPRISE_ALREADY_USE);
        }

        List<Article> articles = articleRepository.findAllByEntrepriseId(id);
        if(!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une entreprise déja utilisé dans des articles", ErrorCodes.ENTREPRISE_ALREADY_USE);
        }
        entrepriseRepository.deleteById(id);
    }
}
