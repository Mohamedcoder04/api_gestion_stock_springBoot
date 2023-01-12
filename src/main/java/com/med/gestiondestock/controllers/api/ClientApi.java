package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_CLIENT;

@Api(ROUTE_CLIENT)
public interface ClientApi {
    @PostMapping(value = ROUTE_CLIENT+"/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Client", notes = "permet d'enregistrer un client après la créeation ou la modification d'un client", response = ClientDto.class)
    ClientDto save(@RequestBody ClientDto clientDto);

    @GetMapping(value = ROUTE_CLIENT+"/client/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Récupérer un client par ID", notes = "permet de récupérer un client par son ID", response = ClientDto.class)
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = ROUTE_CLIENT+"/client/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Récupérer un client par Email", notes = "permet de récupérer un client par son email", response = ClientDto.class)
    ClientDto findByEmail(@PathVariable("email") String email);

    @GetMapping(value = ROUTE_CLIENT+"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Récupérer la liste des clients", notes = "permet de récupérer la liste des clients de la BDD", responseContainer = "List<ClientDto>")
    List<ClientDto> findAll();

    @DeleteMapping(value = ROUTE_CLIENT+"/delete/{idClient}")
    @ApiOperation(value = "supprimer un client", notes = "permet de récupérer un client par son ID")
    void delete(@PathVariable("idClient") Integer id);
}


//*************************** avec openApi *******************************************


//package com.aghzer.gestiondestock.controllers.api;
//
//import com.aghzer.gestiondestock.dto.ClientDto;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.awt.*;
//import java.util.List;
//
//public interface ClientApi {
//    @PostMapping(value = "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet d'enregistrer un client après la créeation ou la modification d'un client")
//    ClientDto save(@RequestBody ClientDto clientDto);
//
//    @GetMapping(value = "/clients/client/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer un client par son ID")
//    ClientDto findById(@PathVariable("idClient") Integer id);
//
//    @GetMapping(value = "/clients/client/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer un client par son email")
//    ClientDto findByEmail(@PathVariable("email") String email);
//
//    @GetMapping(value = "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer les clients de la BDD")
//    List<ClientDto> findAll();
//
//    @DeleteMapping(value = "/clients/delete/{idClient}")
//    @Operation(summary = "permet de récupérer un client par son email")
//    void delete(@PathVariable("idClient") Integer id);
//}
