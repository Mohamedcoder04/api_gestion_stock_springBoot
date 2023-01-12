package com.med.gestiondestock.services;

import com.med.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto client);

    ClientDto findById(Integer id);

    ClientDto findByEmail(String email);

    List<ClientDto> findAll();

    void delete(Integer id);

}
