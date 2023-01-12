package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.MvtStkDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_MVTSTK;

@Api(ROUTE_MVTSTK)
public interface MvtStkApi {

    @GetMapping(value = ROUTE_MVTSTK+"/stockArticle/{idArticle}")
    BigDecimal stockArticle(@PathVariable("idArticle") Integer id);

    @GetMapping(value = ROUTE_MVTSTK+"/listeMvtStck/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStkDto> listeMvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(value = ROUTE_MVTSTK+"/entreeStock")
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping(value = ROUTE_MVTSTK+"/sortieStock")
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping(value = ROUTE_MVTSTK+"/correctionStockPositif")
    MvtStkDto correctionStockPositif(MvtStkDto dto);

    @PostMapping(value = ROUTE_MVTSTK+"/correctionStockNegatif")
    MvtStkDto correctionStockNegatif(MvtStkDto dto);
}
