package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_FOURNISSEUR;

@Api(ROUTE_FOURNISSEUR)
public interface FournisseurApi {
    @PostMapping(value = ROUTE_FOURNISSEUR+"/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "permet d'enregister un fournisseur après sa création ou modification")
    FournisseurDto save(@RequestBody FournisseurDto fournisseurDto);

    @GetMapping(value = ROUTE_FOURNISSEUR+"/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "permet de récuperer un fournisseur par son ID")
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(value = ROUTE_FOURNISSEUR+"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "permet de récupérer les fournisseurs de la BDD")
    List<FournisseurDto> findAll();

    @DeleteMapping(ROUTE_FOURNISSEUR+"/{idFournisseur}")
    @ApiOperation(value = "permet de supprimer le fournisseur par son ID")
    void delete(@PathVariable("idFournisseur") Integer id);
}


//****************************************** avec openApi *******************************************


//package com.aghzer.gestiondestock.controllers.api;
//
//import com.aghzer.gestiondestock.dto.FournisseurDto;
//import com.aghzer.gestiondestock.model.Fournisseur;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import javax.print.attribute.standard.Media;
//import java.util.List;
//
//public interface FournisseurApi {
//    @PostMapping(value = "/fournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet d'enregister un fournisseur après sa création ou modification")
//    FournisseurDto save(@RequestBody FournisseurDto fournisseurDto);
//
//    @GetMapping(value = "/fournisseurs/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récuperer un fournisseur par son ID")
//    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);
//
//    @GetMapping(value = "/fournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer les fournisseurs de la BDD")
//    List<FournisseurDto> findAll();
//
//    @DeleteMapping("/fournisseurs/{idFournisseur}")
//    void delete(@PathVariable("idFournisseur") Integer id);
//}
