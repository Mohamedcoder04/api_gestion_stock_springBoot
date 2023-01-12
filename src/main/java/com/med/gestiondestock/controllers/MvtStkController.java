package com.med.gestiondestock.controllers;

import com.med.gestiondestock.controllers.api.MvtStkApi;
import com.med.gestiondestock.dto.MvtStkDto;
import com.med.gestiondestock.services.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvtStkController implements MvtStkApi {

    private MvtStkService mvtStkService;

    @Autowired
    public MvtStkController(MvtStkService mvtStkService) {
        this.mvtStkService = mvtStkService;
    }

    @Override
    public BigDecimal stockArticle(Integer id) {
        return mvtStkService.stockArticle(id);
    }

    @Override
    public List<MvtStkDto> listeMvtStkArticle(Integer idArticle) {
        return mvtStkService.listeMvtStkArticle(idArticle);
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return mvtStkService.entreeStock(dto);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return mvtStkService.sortieStock(dto);
    }

    @Override
    public MvtStkDto correctionStockPositif(MvtStkDto dto) {
        return mvtStkService.correctionStockPositif(dto);
    }

    @Override
    public MvtStkDto correctionStockNegatif(MvtStkDto dto) {
        return mvtStkService.correctionStockNegatif(dto);
    }
}
