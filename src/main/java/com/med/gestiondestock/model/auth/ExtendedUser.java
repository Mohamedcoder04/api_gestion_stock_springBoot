package com.med.gestiondestock.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// on crée cette classe pour définir l'id entreprise de chaque utilisateur
public class ExtendedUser extends User {

    @Getter
    @Setter
    private Integer idEntreprise;

    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities,
                        Integer idEntreprise) {
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}
