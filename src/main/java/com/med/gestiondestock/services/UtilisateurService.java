package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.ChangePasswordUtilisatuerDto;
import com.med.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto utilisateurDto);
    UtilisateurDto findById(Integer id);
    UtilisateurDto findByEmail(String email);
    List<UtilisateurDto> findAll();
    void delete(Integer id);

    UtilisateurDto updatePassword(ChangePasswordUtilisatuerDto dto);
}
