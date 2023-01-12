package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Vente, Integer> {
}
