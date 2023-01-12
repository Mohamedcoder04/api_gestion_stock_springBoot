package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.ChangePasswordUtilisatuerDto;
import com.med.gestiondestock.dto.UtilisateurDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.model.Utilisateur;
import com.med.gestiondestock.repositories.UtilisateurRepository;
import com.med.gestiondestock.services.UtilisateurService;
import com.med.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateurDto);
        if (!errors.isEmpty()) {
            log.error("User not valid {}", utilisateurDto);
            throw new InvalidEntityException("utilisateur n'est pas valide ", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }
        Utilisateur utilisateur = utilisateurRepository.save(UtilisateurDto.toUtilisateur(utilisateurDto));
        return UtilisateurDto.fromUtilisateur(utilisateur);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null) {
            log.error("id user is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);

        return Optional.of(UtilisateurDto.fromUtilisateur(utilisateur.get())).orElseThrow(
                () -> new EntityNotFoundException("aucun utilisateur n'a l'id " + id, ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        if (!StringUtils.hasLength(email)) {
            log.error("email user is null");
            throw new InvalidEntityException("email que vous avez saisi est null", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByEmail(email);

        return Optional.of(UtilisateurDto.fromUtilisateur(utilisateur.get())).orElseThrow(
                () -> new EntityNotFoundException("aucun utilisateur n'a cet email " + email, ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromUtilisateur)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("id user is null");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto updatePassword(ChangePasswordUtilisatuerDto dto) {
        Utilisateur utilisateur = UtilisateurDto.toUtilisateur(validateUtilisateur(dto));

        utilisateur.setMoteDePasse(dto.getMotDePasse());

        return UtilisateurDto.fromUtilisateur(
                utilisateurRepository.save(utilisateur)
        );
    }


    private UtilisateurDto validateUtilisateur(ChangePasswordUtilisatuerDto dto) {
        UtilisateurDto utilisateurDto = findById(dto.getId());
        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.warn("Mot de passe est null");
            throw new InvalidOperationException("Impossible de modifier le mot de passe avec un mot de passe null", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NULL);
        }
        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("Les mot de passes non conformes");
            throw new InvalidOperationException("Impossible de modifier le mot de passe avec des mot de passes non conformes", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NULL);
        }
        return utilisateurDto;
    }
}
