package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.CommandeFournisseurDto;
import com.med.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.med.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_COMMANDE_FOURNISSEUR;

@Api(ROUTE_COMMANDE_FOURNISSEUR)
public interface CommandeFournisseurApi {
    @PostMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une commande d'un client",
            notes = "permet d'enregistrer une commande d'un client après la création ou la modification",
            response = CommandeFournisseurDto.class)
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/{idCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer une commande d'un client par son ID",
            notes = "permet de récupérer une commande d'un client par son ID",
            response = CommandeFournisseurDto.class)
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/{referenceCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer une commande d'un client par son réference", notes = "permet de récupérer une commande d'un client par son réference",
            response = CommandeFournisseurDto.class)
    ResponseEntity<CommandeFournisseurDto> findByReference(@PathVariable("referenceCommandeClient") String reference);

    @GetMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer les commande d'un client ", notes = "permet de récupérer les commande d'un client de la BDD",
            responseContainer = "List<CommandeFournisseurDto>")
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/{idCommandeClient}")
    @ApiOperation(value = "supprimer une commande d'un client par son ID", notes = "permet de supprimer une commande d'un client de la BDD par son ID")
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id);

    @PatchMapping(value = ROUTE_COMMANDE_FOURNISSEUR +"/updatequantite/{idCommandeFournisseur}/{idLigneCommandeFournisseur}/{quantite}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateQuantity(
            @PathVariable("idCommandeFournisseur") Integer idCommandeFournisseur,
            @PathVariable("idLigneCommandeFournisseur") Integer idLigneCommandeFournisseur,
            @PathVariable("quantite") BigDecimal quantite);
    CommandeFournisseurDto updateFournisseur(Integer idCommandeFournisseur, Integer idFournisseur);

    @PatchMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/updatearticle/{idCommandeFournisseur}/{idLigneCommandeFournisseur}/{idArticle}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateArticle(
            @PathVariable("idCommandeFournisseur") Integer idCommandeFournisseur,
            @PathVariable("idLigneCommandeFournisseur") Integer idLigneCommandeFournisseur,
            @PathVariable("idArticle") Integer idArticle);
    List<LigneCommandeFournisseurDto> findAllLigneCommande(Integer idCommandeFournisseur);

    @DeleteMapping(value = ROUTE_COMMANDE_FOURNISSEUR +"/deletearticle/{idCommandeFournisseur}/{idLigneCommandeFournisseur}")
    CommandeFournisseurDto deleteArticle(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur);

    @PatchMapping(value = ROUTE_COMMANDE_FOURNISSEUR + "/updatearticle/{idCommandeFournisseur}/{etatCommande}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CommandeFournisseurDto updateEtatCommandeFournisseur(
            @PathVariable("idCommandeFournisseur") Integer idCommandeFournisseur,
            @PathVariable("etatCommande") EtatCommande etatCommande);
}
