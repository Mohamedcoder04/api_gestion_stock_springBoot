package com.med.gestiondestock.repositories;

import com.med.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCodeCategory(String codeCategory);
}
