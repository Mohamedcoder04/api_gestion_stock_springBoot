package com.med.gestiondestock.services.implimentation;

import com.aghzer.gestiondestock.dto.*;
import com.med.gestiondestock.dto.*;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.aghzer.gestiondestock.model.*;
import com.med.gestiondestock.model.*;
import com.med.gestiondestock.repositories.ArticleRepository;
import com.med.gestiondestock.repositories.CommandeFournisseurRepository;
import com.med.gestiondestock.repositories.FournisseurRepository;
import com.med.gestiondestock.repositories.LigneCommandeFournisseurRepository;
import com.med.gestiondestock.services.CommandeFournisseurService;
import com.med.gestiondestock.services.MvtStkService;
import com.med.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
    private FournisseurRepository fournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private ArticleRepository articleRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeFournisseurServiceImpl(FournisseurRepository fournisseurRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                                          ArticleRepository articleRepository, CommandeFournisseurRepository commandeFournisseurRepository, MvtStkService mvtStkService) {
        this.fournisseurRepository = fournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.articleRepository = articleRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Commande fournisseur not valid");
            throw new InvalidEntityException("la commande fournisseur n'est pas valid", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (fournisseur.isEmpty()) {
            log.error("");
            //TODO générer l'éxception not found
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligneFournisseur -> {
                if (ligneFournisseur.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneFournisseur.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("");
                    }
                } else {
                    articleErrors.add("Impossible de créer une commande fournisseur avec un article vide");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            //TODO générer l'exception
        }

        CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toCommandeFournisseur(dto));
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligneFournisseur -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toLigneCommandeFournisseur(ligneFournisseur);
                ligneCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromCommandeFournisseur(commandeFournisseur);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("");
        }
        //Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findById(id);

        //return CommandeFournisseurDto.fromCommandeFournisseur(commandeFournisseur.get());
        return commandeFournisseurRepository.findById(id).
                map(CommandeFournisseurDto::fromCommandeFournisseur)
                .orElseThrow(
                        () -> new EntityNotFoundException("aucune commande fournisseur n'a été trouvé", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public CommandeFournisseurDto findByReference(String reference) {
        if (!StringUtils.hasLength(reference)) {
            log.error("");
            throw new InvalidEntityException("" + reference, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID);
        }
        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findByCode(reference);
        return Optional.of(CommandeFournisseurDto.fromCommandeFournisseur(commandeFournisseur.get())).
                orElseThrow(() -> new EntityNotFoundException("aucune commande fournisseur n'a été trouvé", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromCommandeFournisseur)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("");
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur déja utiliser dans des lignecommandefournisseurs", ErrorCodes.COMMANDE_FOURNISSEUR__ALREADY_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateQuantity(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur, BigDecimal quantite) {

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommandeFournisseur(idCommandeFournisseur);
        LigneCommandeFournisseur ligneCommandeFournisseur = checkIdLigneCommandeFournisseur(idLigneCommandeFournisseur);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidOperationException("Impossible de modifier la quantité avec une quantité NULL", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommandeFournisseur, Integer idFournisseur) {
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommandeFournisseur(idCommandeFournisseur);
        Fournisseur fournisseur = checkIdFournisseur(idFournisseur);
        commandeFournisseurDto.setFournisseur(FournisseurDto.fromFournisseur(fournisseur));
        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur, Integer idArticle) {
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommandeFournisseur(idCommandeFournisseur);
        LigneCommandeFournisseur ligneCommandeFournisseur = checkIdLigneCommandeFournisseur(idLigneCommandeFournisseur);
        Article article = checkIdArticle(idArticle);
        ligneCommandeFournisseur.setArticle(article);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseurDto;
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLigneCommande(Integer idCommandeFournisseur) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommandeFournisseur).stream()
                .map(LigneCommandeFournisseurDto::fromLigneCommandeFournisseur)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur) {
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommandeFournisseur(idCommandeFournisseur);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommandeFournisseur);
        checkIdLigneCommandeFournisseur(idLigneCommandeFournisseur);
        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto updateEtatCommandeFournisseur(Integer idCommandeFournisseur, EtatCommande etatCommande) {
        if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            throw new InvalidOperationException("Vous ne pouvez pas modifier l'état de la commande fournisseur avec un etat null"
                    , ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommandeFournisseur(idCommandeFournisseur);
        commandeFournisseurDto.setEtatCommande(etatCommande);
        if(commandeFournisseurDto.isCommandeLivree()){
            updateMvtStk(idCommandeFournisseur);
        }
        return commandeFournisseurDto;
    }

    private CommandeFournisseurDto checkEtatCommandeFournisseur(Integer id) {
        CommandeFournisseurDto commandeFournisseurDto = findById(id);
        if (commandeFournisseurDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier une commande fournisseur livrée");
        }
        return commandeFournisseurDto;
    }

    private LigneCommandeFournisseur checkIdLigneCommandeFournisseur(Integer id) {
        if (id == null) {
            throw new InvalidOperationException("Impossible de modifier la quantite d'une commande avec un ID null de la Ligne de commande fournisseur ");
        }
        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("aucune ligne commande fournisseur avec l'Id" + id + "n'a été trouvé",
                        ErrorCodes.LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND)
        );

        return ligneCommandeFournisseur;
    }

    private Fournisseur checkIdFournisseur(Integer id) {
        if (id == null) {
            throw new InvalidOperationException("Impossible de modifier une commande fournisseur avec un id fournisseur NULL", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return fournisseurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("", ErrorCodes.FOURNISSEUR_NOT_FOUND)
        );
    }

    private Article checkIdArticle(Integer id) {
        if (id == null) {
            throw new InvalidOperationException("Impossible de modifier l'article d'une commande fournisseur avec un article ID NULL", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("aucun article avec l'ID" + id + " n'a été trouvé", ErrorCodes.ARTICLE_NOT_FOUND)
        );

        return article;
    }

    private void updateMvtStk(Integer idCommandeFournisseur){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs =
                ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommandeFournisseur);
        ligneCommandeFournisseurs.forEach(ligneCommandeFournisseur -> {
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .dateMvt(Instant.now())
                    .quantite(ligneCommandeFournisseur.getQuantite())
                    .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                    .article(ArticleDto.fromArticle(ligneCommandeFournisseur.getArticle()))
                    .sourceMvtStk(SourceMvtStk.COMMANDT_FOURNISSEUR)
                    .typeMvt(TypeMvt.ENTREE)
                    .build();

            mvtStkService.entreeStock(mvtStkDto);
        });

    }
}
