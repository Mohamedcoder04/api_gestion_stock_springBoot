package com.med.gestiondestock.security.authentication;

import com.med.gestiondestock.dto.UtilisateurDto;
import com.med.gestiondestock.model.auth.ExtendedUser;
import com.med.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtilisateurDto utilisateur = utilisateurService.findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (utilisateur.getRoles() != null) {
            utilisateur.getRoles().forEach(
                    role -> {
                        authorities.add(
                                new SimpleGrantedAuthority(
                                        role.getRoleName()
                                )
                        );
                    }
            );
        }

        return new ExtendedUser(
                utilisateur.getEmail(), utilisateur.getMotDePasse(),
                authorities, utilisateur.getEntreprise().getId()
        );
    }
}