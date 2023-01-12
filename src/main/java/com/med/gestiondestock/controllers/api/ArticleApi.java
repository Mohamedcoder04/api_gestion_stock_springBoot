//package com.aghzer.gestiondestock.controllers.api;
//
//import com.aghzer.gestiondestock.dto.ArticleDto;
//import com.aghzer.gestiondestock.model.Article;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//public interface ArticleApi {
//
//    @PostMapping(value = "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de créer un article")
//    ArticleDto save(@RequestBody ArticleDto articleDto);
//
//    @GetMapping(value = "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de chercher un article par son ID")
//    ArticleDto findById(@PathVariable("idArticle") Integer id); //on définit le nom c'est les nomVariables ne sont pas les même
//
//    @GetMapping(value = "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de chercher un article par son code")
//    ArticleDto findByCodeArticle(@PathVariable String codeArticle);
//
//    @GetMapping(value = "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "permet de récupérer les article de la BDD")
//    List<ArticleDto> findAll();
//
//    @DeleteMapping(value = "/articles/delete/{idArticle}")
//    @Operation(summary = "permet de supprimer un article")
//    void delete(@PathVariable("idArticle") Integer id);
//}

 //documentation avec swagger ms j'ai un erreur et je suis en train de cherhcer la solutions

package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.ArticleDto;
import com.med.gestiondestock.dto.LigneCommandeClientDto;
import com.med.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.med.gestiondestock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_ARTICLES;

@Api( ROUTE_ARTICLES)
public interface ArticleApi {

    @PostMapping(value = ROUTE_ARTICLES+"/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer un article", notes = "permet de créer ou modifier un article", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cet article a été bien enregistré"),
            @ApiResponse(code = 400, message = "votre article a des champs manqués")
    })
    ArticleDto save(@RequestBody ArticleDto articleDto);

    @GetMapping(value = ROUTE_ARTICLES+"/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article", notes = "permet de chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cet article a été bien trouvé"),
            @ApiResponse(code = 404, message = "aucun article avec cet ID n'a été trouvé dans la BDD ")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id); //on définit le nom c'est les nomVariables ne sont pas les même

    @GetMapping(value = ROUTE_ARTICLES+"/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article", notes = "permet de chercher un article par son CODE", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cet article a été bien trouvé"),
            @ApiResponse(code = 404, message = "aucun article avec ce CODE n'a été trouvé dans la BDD ")
    })
    ArticleDto findByCodeArticle(@PathVariable String codeArticle);

    @GetMapping(value = ROUTE_ARTICLES+ "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Récupérer la liste des articles", notes = "Récupérer la liste des articles", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "voilà la liste des articles existent dans la BDD / peut-être elle est vide"),
            @ApiResponse(code = 404, message = "si il y a un erreur")
    })
    List<ArticleDto> findAll();

    @DeleteMapping(value = ROUTE_ARTICLES+"/delete/{idArticle}")
    @ApiOperation(value = "supprimer un article", notes = "permet de supprimer un article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cet article a été bien supprimé"),
            @ApiResponse(code = 404, message = "vous avez un erreur")
    })
    void delete(@PathVariable("idArticle") Integer id);

    @GetMapping(value = ROUTE_ARTICLES+"/historique/ventes/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = ROUTE_ARTICLES+"/historique/commandesclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle")Integer idArticle);

    @GetMapping(value = ROUTE_ARTICLES+"/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle")Integer idArticle);

    @GetMapping(value = ROUTE_ARTICLES+"/filter/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAllArticleByCategory(@PathVariable("idCategory")Integer idCategory);


}
