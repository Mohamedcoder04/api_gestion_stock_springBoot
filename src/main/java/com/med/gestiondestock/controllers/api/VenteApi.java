package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.VenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_VENTE;

@Api(ROUTE_VENTE)
public interface VenteApi {
    @PostMapping(value = ROUTE_VENTE + "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",
            notes = "",
            response = VenteDto.class)
    VenteDto save(@RequestBody VenteDto venteDto);

    @GetMapping(value = ROUTE_VENTE + "/utilisateur/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "", notes = "", response = VenteDto.class)
    VenteDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = ROUTE_VENTE + "/utilisateur/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "", notes = "", response = VenteDto.class)
    VenteDto findByCode(String code);

    @GetMapping(value = ROUTE_VENTE + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "", notes = "", responseContainer = "List<VenteDto>")
    List<VenteDto> findAll();

    @DeleteMapping(value = ROUTE_VENTE + "/delete/{idVente}")
    @ApiOperation(value = "", notes = "")
    void delete(@PathVariable("idVente") Integer id);

}
