package com.med.gestiondestock.security.controller;

import com.med.gestiondestock.dto.auth.AuthenticationRequest;
import com.med.gestiondestock.dto.auth.AuthenticationResponse;
import com.med.gestiondestock.model.auth.ExtendedUser;
import com.med.gestiondestock.security.authentication.ApplicationUserDetailsService;
import com.med.gestiondestock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.med.gestiondestock.utils.Constants.APP_ROUTE;

@RestController
@RequestMapping(APP_ROUTE+"/auth")
public class AuthenticationController {

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    //il va lever une exception badCredentielsException si les données sont incorrectes
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        //TODO il va vérifier que l'émail et le motdepasse s'il existe dans la BDD ou non
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String jwt = jwtUtil.generateToken( (ExtendedUser) userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }


}
