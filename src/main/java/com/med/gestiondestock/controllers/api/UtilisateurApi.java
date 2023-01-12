package com.med.gestiondestock.controllers.api;

import com.med.gestiondestock.dto.ChangePasswordUtilisatuerDto;
import com.med.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.med.gestiondestock.utils.Constants.ROUTE_UTILISATEUR;

@Api(ROUTE_UTILISATEUR)
public interface UtilisateurApi {
    @PostMapping(value = ROUTE_UTILISATEUR+"/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",
                    notes = "",
                    response = UtilisateurDto.class)
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateurDto);

    @GetMapping(value = ROUTE_UTILISATEUR+"/utilisateur/{idUtilisateur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",notes = "", response = UtilisateurDto.class)
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

   /* @GetMapping(value = ROUTE_UTILISATEUR+"/utilisateur/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",notes = "", response = UtilisateurDto.class)
    UtilisateurDto findByEmail( String email); */

    @GetMapping(value = ROUTE_UTILISATEUR+"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "",notes = "", responseContainer = "List<UtilisateurDto>")
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = ROUTE_UTILISATEUR +"/delete/{idUtilisateur}")
    @ApiOperation(value = "",notes = "")
    void delete(@PathVariable("idUtilisateur") Integer id);

    @PostMapping(value = ROUTE_UTILISATEUR+"/changemotdepasse")
    UtilisateurDto updatePassword(@RequestBody ChangePasswordUtilisatuerDto dto);

}
