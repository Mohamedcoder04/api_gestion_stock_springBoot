package com.med.gestiondestock.controllers;

import com.med.gestiondestock.controllers.api.UtilisateurApi;
import com.med.gestiondestock.dto.ChangePasswordUtilisatuerDto;
import com.med.gestiondestock.dto.UtilisateurDto;
import com.med.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UtilisateurController implements UtilisateurApi {
    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        return utilisateurService.save(utilisateurDto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurService.findById(id);
    }

    /*
    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurService.findByEmail(email);
    }
    */

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }

    @Override
    public UtilisateurDto updatePassword(ChangePasswordUtilisatuerDto dto) {
        return utilisateurService.updatePassword(dto);
    }
}
