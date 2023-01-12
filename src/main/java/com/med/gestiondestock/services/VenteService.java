package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.VenteDto;

import java.util.List;

public interface VenteService {
    VenteDto save(VenteDto venteDto);
    VenteDto findById(Integer id);
    VenteDto findByCode(String code);
    List<VenteDto> findAll();
    void delete(Integer id);

}
