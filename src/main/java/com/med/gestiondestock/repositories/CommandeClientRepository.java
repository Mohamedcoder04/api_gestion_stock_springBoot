package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {
    Optional<CommandeClient> findByReference(String reference);
    List<CommandeClient> findAllByClientId(Integer id);
}
