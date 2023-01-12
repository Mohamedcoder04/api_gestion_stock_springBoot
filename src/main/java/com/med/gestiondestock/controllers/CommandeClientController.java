package com.med.gestiondestock.controllers;


import com.med.gestiondestock.controllers.api.CommandeClientApi;
import com.med.gestiondestock.dto.CommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.model.EtatCommande;
import com.med.gestiondestock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeClientController implements CommandeClientApi {
    private CommandeClientService commandeClientService;

    @Autowired
    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        //return ResponseEntity.ok(commandeClientService.save(dto));

        // si je veux juste retourner que l'objet est créé sans le retourné
        commandeClientService.save(dto); // on fait l'neregistrement
        return ResponseEntity.status(HttpStatus.CREATED).build(); // il va retourner le code 201, que il est créé
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommandeClient(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeClientService.updateEtatCommandeClient(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeClientService.updateQuantiteCommandee(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.ok(commandeClientService.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.ok(commandeClientService.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientDto>> listeLigneCommandeClientByCommande(Integer idCommande) {
        return ResponseEntity.ok(commandeClientService.listeLigneCommandeClientByCommande(idCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer id) {
        return ResponseEntity.ok(commandeClientService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByReference(String reference) {
        return ResponseEntity.ok(commandeClientService.findByReference(reference));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
