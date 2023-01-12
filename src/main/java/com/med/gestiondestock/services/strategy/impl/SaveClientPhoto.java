package com.med.gestiondestock.services.strategy.impl;

import com.med.gestiondestock.dto.ClientDto;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.services.ClientService;
import com.med.gestiondestock.services.FlickService;
import com.med.gestiondestock.services.strategy.Strategy;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {
    private FlickService flickService;
    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(FlickService flickService, ClientService clientService) {
        this.flickService = flickService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ClientDto clientDto = clientService.findById(id);
        String urlPhoto = flickService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDto.setPhoto(urlPhoto);
        return clientService.save(clientDto);
    }
}
