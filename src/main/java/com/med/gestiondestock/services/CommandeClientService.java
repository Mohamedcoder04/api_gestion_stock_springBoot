package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.CommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {
    CommandeClientDto save(CommandeClientDto dto);

    CommandeClientDto findById(Integer id);
    CommandeClientDto findByReference(String reference);
    List<CommandeClientDto> findAll();
    void delete(Integer id);

    CommandeClientDto updateEtatCommandeClient(Integer idCommande, EtatCommande etatCommande);

    CommandeClientDto updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeClientDto updateClient(Integer idCommandeClient, Integer idClient);

    CommandeClientDto updateArticle(Integer idCommande,Integer idLigneCommande, Integer idArticle);

    //TODO ça équivalent à deleteLigneCommande
    CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    List<LigneCommandeClientDto> listeLigneCommandeClientByCommande(Integer idCommande);


}
