package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.CommandeFournisseurDto;
import com.med.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.med.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByReference(String reference);

    List<CommandeFournisseurDto> findAll();

    void delete(Integer id);

    CommandeFournisseurDto updateQuantity(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur, BigDecimal quantite);
    CommandeFournisseurDto updateFournisseur(Integer idCommandeFournisseur, Integer idFournisseur);
    CommandeFournisseurDto updateArticle(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur, Integer idArticle);
    List<LigneCommandeFournisseurDto> findAllLigneCommande(Integer idCommandeFournisseur);
    CommandeFournisseurDto deleteArticle(Integer idCommandeFournisseur, Integer idLigneCommandeFournisseur);
    CommandeFournisseurDto updateEtatCommandeFournisseur(Integer idCommandeFournisseur, EtatCommande etatCommande);
}
