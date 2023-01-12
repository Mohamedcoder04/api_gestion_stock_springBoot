package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.ClientDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.model.Client;
import com.med.gestiondestock.model.CommandeClient;
import com.med.gestiondestock.repositories.ClientRepository;
import com.med.gestiondestock.repositories.CommandeClientRepository;
import com.med.gestiondestock.repositories.LigneCommandeClientRepository;
import com.med.gestiondestock.services.ClientService;
import com.med.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, LigneCommandeClientRepository ligneCommandeClientRepository){
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> errors = ClientValidator.validate(clientDto);
        if(!errors.isEmpty()){
            log.error("Client not valid {}", clientDto);
            throw new InvalidEntityException("le Client "+clientDto+" n'est pas valid ", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        Client client = clientRepository.save(ClientDto.toClient(clientDto));
        ClientDto dto = ClientDto.fromClient(client);
        return dto;
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null){
            log.error("l'id client is null");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);
        ClientDto dto  = ClientDto.fromClient(client.get());
        return Optional.of(dto).orElseThrow(
                        ()-> new EntityNotFoundException("aucun client n'a l'id "+ id, ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public ClientDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("l'email du client is null");
            return null;
        }
        Optional<Client> client = clientRepository.findByEmail(email);
        return Optional.of(ClientDto.fromClient(client.get())).orElseThrow(
                ()-> new EntityNotFoundException("aucun client n'a l'email "+email, ErrorCodes.CLIENT_NOT_FOUND)
        );
    }


    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("l'id client is null");
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if(!commandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un client qui a déja des commandeclient", ErrorCodes.CLIENT_ALREADY_USE);
        }
        clientRepository.deleteById(id);
    }
}
