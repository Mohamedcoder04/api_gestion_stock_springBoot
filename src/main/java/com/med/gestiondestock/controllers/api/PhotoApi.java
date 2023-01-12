package com.med.gestiondestock.controllers.api;


import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.med.gestiondestock.utils.Constants.ROUTE_PHOTO;

@Api(ROUTE_PHOTO)
public interface PhotoApi {

    @PostMapping(ROUTE_PHOTO+"/{id}/{titre}/{context}")
    Object savePhoto(String context, Integer id, @RequestPart("file") MultipartFile photo , String titre) throws IOException, FlickrException;
}
