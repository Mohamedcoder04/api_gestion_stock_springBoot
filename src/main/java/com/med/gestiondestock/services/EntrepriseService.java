package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto entrepriseDto);
    EntrepriseDto findById(Integer id);
    List<EntrepriseDto> findAll();
    void delete(Integer id);
}
