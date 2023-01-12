package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    //TODO L'annotation n'est pas obligatoire
    //TODO JPQL Query
    //@Query(value = "select u from Utilisateur u where u.email = :email")
    Optional<Utilisateur> findUtilisateurByEmail(/*@Param("email")*/ String email);
    List<Utilisateur> findAllByEntrepriseId(Integer id);
}
