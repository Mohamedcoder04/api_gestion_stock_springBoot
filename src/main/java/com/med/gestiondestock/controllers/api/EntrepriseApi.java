package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_ENTREPRISE;

@Api(ROUTE_ENTREPRISE)
public interface EntrepriseApi {
    @PostMapping(value = ROUTE_ENTREPRISE+"/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une entreprise", notes = "permet de créer ou modifier une entreprise", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été bien enregistré"),
            @ApiResponse(code = 400, message = "votre entreprise a des champs manqués")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto entrepriseDto);

    @GetMapping(value = ROUTE_ENTREPRISE+"/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une entreprise", notes = "permet de chercher une entreprise par son ID", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "l'entreprise a été bien trouvé"),
            @ApiResponse(code = 404, message = "aucune entreprise avec cet ID n'a été trouvé dans la BDD ")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(value = ROUTE_ENTREPRISE+"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Récupérer la liste des entreprises", notes = "Récupérer la liste des entreprises de la BDD", responseContainer = "List<EntrepriseDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "voilà la liste des entreprises existent dans la BDD / peut-être elle est vide"),
            @ApiResponse(code = 404, message = "si il y a un erreur")
    })
    List<EntrepriseDto> findAll();

    @DeleteMapping(value = ROUTE_ENTREPRISE+"/delete/{idEntreprise}")
    @ApiOperation(value = "supprimer une entreprise", notes = "permet de supprimer une entreprise")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été bien supprimé"),
            @ApiResponse(code = 404, message = "vous avez un erreur")
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}
