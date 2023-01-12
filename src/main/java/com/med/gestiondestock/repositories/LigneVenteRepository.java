package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    List<LigneVente> findAllByArticleId(Integer idArticle);
    List<LigneVente> findAllByVenteId(Integer idVente);
}
