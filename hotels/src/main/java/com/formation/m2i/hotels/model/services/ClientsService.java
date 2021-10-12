package com.formation.m2i.hotels.model.services;

import com.formation.m2i.hotels.model.entities.ClientsEntity;
import com.formation.m2i.hotels.model.repositories.ClientsRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    private final ClientsRepository clientsRepository;

    public ClientsService(ClientsRepository clientsRep) {
        this.clientsRepository = clientsRep;
    }

    public List<ClientsEntity> getAllClients() {
        return (List<ClientsEntity>)clientsRepository.findAll();
    }

    public Optional<ClientsEntity> getClientById(final int id){
        return clientsRepository.findById(id);
    }

    public ClientsEntity setClient(final ClientsEntity client, final String completeName,
                                   final String phone, final String email,
                                   final String address){
        client.setCompleteName(completeName);
        client.setPhone(phone);
        client.setEmail(email);
        client.setAddress(address);
        return client;
    }

    @Transactional
    public ClientsEntity saveClient(ClientsEntity client){
        return clientsRepository.save(client);
    }

    public ClientsEntity addClient(final String completeName, final String phone,
                                   final String email, final String address){
        return saveClient(setClient(new ClientsEntity(), completeName, phone, email, address));
    }

    public ClientsEntity updateClientById(final int id,final String completeName,
                                          final String phone, final String email,
                                          final String address) {
        Optional<ClientsEntity> optionalClient = getClientById(id);
        ClientsEntity client;
        if(optionalClient.isPresent()) {
            client = optionalClient.get();
            return saveClient(setClient(client, completeName, phone, email, address));
        } else {
            throw new ObjectNotFoundException(id, "Client not found");
        }
    }

    @Transactional
    public void deleteClientById(final int id) {
        Optional<ClientsEntity> clientToDelete = getClientById(id);
        if(clientToDelete.isPresent()) {
            clientsRepository.delete(clientToDelete.get());
        } else {
            throw new ObjectNotFoundException(id, "Client not found");
        }
    }
}
