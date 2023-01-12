package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.services.FlickService;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FlickrServiceImpl implements FlickService {
    private Flickr flickr;

    @Autowired
    public FlickrServiceImpl(Flickr flickr) {
        this.flickr = flickr;
    }

    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);
        String photoId = flickr.getUploader().upload(photo, uploadMetaData); //génèrer une exception
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }
}
