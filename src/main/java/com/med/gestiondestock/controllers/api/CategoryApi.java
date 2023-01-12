package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_CATEGORY;

@Api(ROUTE_CATEGORY)
public interface CategoryApi {
    @PostMapping(value = ROUTE_CATEGORY+"/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="enregistrer une categorie", notes = "permet d'enregistrer la catégorie aprés la création ou la modification", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "la catégorie a été bien enregistré"),
            @ApiResponse(code = 400, message = "votre catégorie a des champs manqués")
    })
    CategoryDto save(@RequestBody CategoryDto categoryDto);

    @GetMapping(value = ROUTE_CATEGORY+"/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Récupérer une catégorie par Id",notes = "permet de chercher la catégorie par son ID", response = ArticleDto.class)
    CategoryDto findById(@PathVariable("idCategory") Integer id);

    @GetMapping(value = ROUTE_CATEGORY+"/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Récupérer une catégorie par Code", notes = "permet de chercher la catégorie par son code")
    CategoryDto findByCodeArticle(@Param(value = "accept values [Cat1,Cat2,Cat3]") @PathVariable("codeCategory") String codeCategory);

    @GetMapping(value = ROUTE_CATEGORY+"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Récupérer la liste des catégories", notes = "permet de récupérer la liste des catégories de la BDD")
    List<CategoryDto> findAll();

    @DeleteMapping(value = ROUTE_CATEGORY+"categories/delete/{idCategory}")
    @ApiOperation(value ="Supprimer une catégorie", notes = "permet de supprimer une catégorie")
    void delete(@PathVariable("idCategory") Integer id);
}



// *********************************  avec openApi  *********************************

//package com.aghzer.gestiondestock.controllers.api;
//
//import com.aghzer.gestiondestock.dto.CategoryDto;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//public interface CategoryApi {
//    @PostMapping(value = "/categories/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet d'enregistrer la catégorie aprés la création ou la modification")
//    CategoryDto save(@RequestBody CategoryDto categoryDto);
//
//    @GetMapping(value = "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de chercher la catégorie par son ID")
//    CategoryDto findById(@PathVariable("idCategory") Integer id);
//
//    @GetMapping(value = "/categories/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de chercher la catégorie par son code")
//    CategoryDto findByCodeArticle(@Parameter(description = "accept values [Cat1,Cat2,Cat3]") @PathVariable("codeCategory") String codeCategory);
//
//    @GetMapping(value = "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer les catégories de la BDD")
//    List<CategoryDto> findAll();
//
//    @DeleteMapping(value = "/categories/delete/{idCategory}")
//    @Operation(summary = "permet de supprimer une catégorie")
//    void delete(@PathVariable("idCategory") Integer id);
//}
