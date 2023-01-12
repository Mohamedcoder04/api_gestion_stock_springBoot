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
import com.med.gestiondestock.repositories.ClientRepository;
import com.med.gestiondestock.repositories.CommandeClientRepository;
import com.med.gestiondestock.repositories.LigneCommandeClientRepository;
import com.med.gestiondestock.services.CommandeClientService;
import com.med.gestiondestock.services.MvtStkService;
import com.med.gestiondestock.validator.CommandeClientValidator;
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
public class CommandeClientServiceImpl implements CommandeClientService {

    private ArticleRepository articleRepository;
    private ClientRepository clientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private CommandeClientRepository commandeClientRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeClientServiceImpl(ArticleRepository articleRepository, ClientRepository clientRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository,
                                     CommandeClientRepository commandeClientRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.clientRepository = clientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {

        List<String> errors = CommandeClientValidator.validate(commandeClientDto);
        if (!errors.isEmpty()) {
            log.error("Commande client not valid");
            throw new InvalidEntityException("la commande client n'est pas valid", ErrorCodes.COMMANDE_CLEINT_NOT_VALID, errors);
        }

        if (commandeClientDto.getId() != null && commandeClientDto.isCommandeLivree()) {
            throw new InvalidOperationException("Vous pouvez pas modifier une commande livrée", ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }

        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId());
        // TODO vérification client si il existe dans la base de données
        if (client.isEmpty()) {
            log.warn("Client with ID {} was not found ", commandeClientDto.getClient().getId());
            throw new EntityNotFoundException("aucun client abec l'ID " + commandeClientDto.getClient().getId() + " n'a été trouvé dans la BDD", ErrorCodes.COMMANDE_CLEINT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligneClient -> {
                if (ligneClient.getArticle() != null) {

                    Optional<Article> article = articleRepository.findById(ligneClient.getArticle().getId());
                    // TODO vérification article si il existe dans la base de données
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'Id" + ligneClient.getArticle().getId() + " n'existe pas");
                    }
                } else {
                    // TODO si l'article est null
                    articleErrors.add("Impossible de créer une commande client avec un article vide");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        // TODO enregistrer la commande Client
        CommandeClient savedCommandeClient = commandeClientRepository.save(CommandeClientDto.toCommandeClient(commandeClientDto));

        if (commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligneClient -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toLigneCommandeClient(ligneClient);
                ligneCommandeClient.setCommandeClient(savedCommandeClient);
                ligneCommandeClientRepository.save(ligneCommandeClient);
            });
        }

        return CommandeClientDto.fromCommandeClient(savedCommandeClient);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            return null;
        }

        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromCommandeClient)
                .orElseThrow(
                        () -> new EntityNotFoundException("aucune commande article n'a été trouvé avec l'ID " + id, ErrorCodes.COMMANDE_CLEINT_NOT_FOUND)
                );
    }

    @Override
    public CommandeClientDto findByReference(String reference) {
        if (!StringUtils.hasLength(reference)) {
            log.error("Commande client reference is NULL");
            return null;
        }

        return commandeClientRepository.findByReference(reference).
                map(CommandeClientDto::fromCommandeClient)
                .orElseThrow(
                        () -> new EntityNotFoundException("aucune commande article n'a été trouvé avec le reference " + reference, ErrorCodes.COMMANDE_CLEINT_NOT_FOUND)
                );
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromCommandeClient)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande client déja utiliser dans des lignecommandeclients", ErrorCodes.COMMANDE_CLEINT__ALREADY_USE);
        }
        commandeClientRepository.deleteById(id);
    }


    @Override
    public CommandeClientDto updateEtatCommandeClient(Integer idCommande, EtatCommande etatCommande) {
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            throw new InvalidOperationException("Vous ne pouvez pas modifier l'état de la commande avec un etat null"
                    , ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        commandeClientDto.setEtatCommande(etatCommande);
        CommandeClientDto commandeClient = CommandeClientDto.fromCommandeClient(commandeClientRepository.save(CommandeClientDto.toCommandeClient(commandeClientDto)));
        if(commandeClient.isCommandeLivree()){
            updateMvtStk(idCommande);
        }
        return commandeClient;
    }


    @Override
    public CommandeClientDto updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        LigneCommandeClient ligneCommandeClient = ligneCommandeClientRepository.findById(idLigneCommande).orElseThrow(
                () -> new EntityNotFoundException("aucune ligne commande avec l'id" + idLigneCommande + " n'a été trouvé", ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND)
        );

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidOperationException("Impossible de modifier la quantité d'une commande avec une quantité null", ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);
        return commandeClientDto;
    }



    @Override
    public CommandeClientDto updateClient(Integer idCommandeClient, Integer idClient) {

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommandeClient);

        Client client = checkIdClient(idClient);
        commandeClientDto.setClient(ClientDto.fromClient(client));

        return commandeClientDto;
    }



    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        LigneCommandeClient ligneCommandeClient = checkIdLigneCommande(idLigneCommande);
        Article article = checkIdArticle(idArticle);
        ligneCommandeClient.setArticle(article);
        ligneCommandeClientRepository.save(ligneCommandeClient);
        return commandeClientDto;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);
        return commandeClientDto;
    }

    //  TODO       ????? confirmer le test
    @Override
    public List<LigneCommandeClientDto> listeLigneCommandeClientByCommande(Integer idCommande) {
        CommandeClientDto commandeClientDto = findById(idCommande);
        //List<LigneCommandeClientDto> dtoList = commandeClientDto.getLigneCommandeClients();
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromLigneCommandeClient)
                .collect(Collectors.toList());
    }


    private CommandeClientDto checkEtatCommande(Integer idCommandeClient) {
        CommandeClientDto commandeClientDto = findById(idCommandeClient);
        if (commandeClientDto.isCommandeLivree()) {
            throw new InvalidOperationException("Vous pouvez pas modifier une commande livrée", ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        return commandeClientDto;
    }

    private LigneCommandeClient checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            throw new InvalidOperationException("Impossible de modifier la quantite d'une commande avec une Ligne de commande null", ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        return ligneCommandeClientRepository.findById(idLigneCommande).orElseThrow(
                () -> new EntityNotFoundException("aucune ligne commande n'a été trouvé avec l'ID" + idLigneCommande,
                        ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND)
        );
    }

    private Client checkIdClient(Integer idClient) {
        if (idClient == null) {
            log.error("L'ID client is null");
            throw new InvalidOperationException("Impossible de modifier un client avec un ID client null",
                    ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        return clientRepository.findById(idClient).orElseThrow(
                () -> new EntityNotFoundException("", ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    private Article checkIdArticle(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'ID article is null");
            throw new InvalidOperationException("Impossible de modifier un article avec un ID article null",
                    ErrorCodes.COMMANDE_CLEINT_NON_MODIFIABLE);
        }
        return articleRepository.findById(idArticle).orElseThrow(
                () -> new EntityNotFoundException("aucun article n'a été trouvé avec l'ID" + idArticle,
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    private void updateMvtStk(Integer idCommandeClient){
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommandeClient);
        ligneCommandeClients.forEach(ligneCommandeClient -> {
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .quantite(ligneCommandeClient.getQuantite())
                    .dateMvt(Instant.now())
                    .idEntreprise(ligneCommandeClient.getIdEntreprise())
                    .typeMvt(TypeMvt.SORTIE)
                    .article(ArticleDto.fromArticle(ligneCommandeClient.getArticle()))
                    .sourceMvtStk(SourceMvtStk.COMMANDE_CLIENT)
                    .build();
            mvtStkService.sortieStock(mvtStkDto);
        });
    }
}
