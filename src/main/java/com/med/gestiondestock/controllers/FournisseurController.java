package com.med.gestiondestock.controllers;

import com.med.gestiondestock.controllers.api.FournisseurApi;
import com.med.gestiondestock.dto.FournisseurDto;
import com.med.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController  implements FournisseurApi {

    private FournisseurService fournisseurService;
    @Autowired
    public FournisseurController(FournisseurService fournisseurService){
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto save(FournisseurDto fournisseurDto) {
        return fournisseurService.save(fournisseurDto);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        return fournisseurService.findById(id);
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        fournisseurService.delete(id);
    }
}
