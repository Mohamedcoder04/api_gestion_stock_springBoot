package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.CommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_COMMANDE_CLIENT;

@Api(ROUTE_COMMANDE_CLIENT)
public interface CommandeClientApi {
    @PostMapping(value = ROUTE_COMMANDE_CLIENT + "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une commande d'un client", notes = "permet d'enregistrer une commande d'un client après la création ou la modification", response = CommandeClientDto.class)
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @PatchMapping(value = ROUTE_COMMANDE_CLIENT + "/updateetatcommande/{idCommande}/{etatCommande}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateEtatCommandeClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = ROUTE_COMMANDE_CLIENT + "/updatequantitecommande/{idCommande}/{idLigneCommande}/{quantite}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateQuantiteCommandee(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = ROUTE_COMMANDE_CLIENT + "/updateclient/{idCommande}/{idClient}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(value = ROUTE_COMMANDE_CLIENT + "/updatearticle/{idCommande}/{idLigneCommande}/{idArticle}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(value = ROUTE_COMMANDE_CLIENT + "/deletearticle/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("{idCommande}") Integer idCommande, @PathVariable("{idLigneCommande}") Integer idLigneCommande);

    @GetMapping(value = ROUTE_COMMANDE_CLIENT + "/lignescommande/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneCommandeClientDto>> listeLigneCommandeClientByCommande(@PathVariable("idCommande") Integer idCommande);







    @GetMapping(value = ROUTE_COMMANDE_CLIENT + "/{idCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer une commande d'un client par son ID", notes = "permet de récupérer une commande d'un client par son ID", response = CommandeClientDto.class)
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value = ROUTE_COMMANDE_CLIENT + "/{referenceCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer une commande d'un client par son réference", notes = "permet de récupérer une commande d'un client par son réference", response = CommandeClientDto.class)
    ResponseEntity<CommandeClientDto> findByReference(@PathVariable("referenceCommandeClient") String reference);

    @GetMapping(value = ROUTE_COMMANDE_CLIENT + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "récupérer les commande d'un client ", notes = "permet de récupérer les commande d'un client de la BDD", responseContainer = "List<CommandeClientDto>")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(value = ROUTE_COMMANDE_CLIENT + "/{idCommandeClient}")
    @ApiOperation(value = "supprimer une commande d'un client par son ID", notes = "permet de supprimer une commande d'un client de la BDD par son ID")
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id);
}
