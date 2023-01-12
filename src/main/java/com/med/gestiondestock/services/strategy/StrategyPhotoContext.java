package com.med.gestiondestock.services.strategy;

import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.aghzer.gestiondestock.services.strategy.impl.*;
import com.flickr4java.flickr.FlickrException;
import com.med.gestiondestock.services.strategy.impl.*;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {
    private BeanFactory beanFactory;
    @Setter
    private String context;
    private Strategy strategy;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Integer id, InputStream photo , String titre) throws FlickrException {
        determineContext(context);
        return strategy.savePhoto(id, photo, titre);
    }

    private void determineContext(String context){
        final String beanName = context + "Strategy";
        switch (context){
            case "article": beanFactory.getBean(beanName, SaveArticlePhoto.class);
                break;
            case "client": beanFactory.getBean(beanName, SaveClientPhoto.class);
                break;
            case "fournisseur": beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
                break;
            case "entreprise": beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
                break;
            case "utilisateur": beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
                break;
            default: throw new InvalidOperationException("Context inconnue", ErrorCodes.CONTEXT_INCONNU);
        }
    }
}
