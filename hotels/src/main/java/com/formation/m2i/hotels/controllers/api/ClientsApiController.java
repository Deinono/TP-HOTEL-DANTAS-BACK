package com.formation.m2i.hotels.controllers.api;

import com.formation.m2i.hotels.model.entities.ClientsEntity;
import com.formation.m2i.hotels.model.services.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientsApiController {

    private ClientsService clientsService;

    public ClientsApiController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping(path = "", produces = "application/json")
    public List<ClientsEntity> getAllClientsApi(){
        return clientsService.getAllClients();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ClientsEntity getClientById(@PathVariable("id") final int id){
        Optional<ClientsEntity> optionalClient = clientsService.getClientById(id);
        if(optionalClient.isPresent()) {
            return optionalClient.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No client found.");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<ClientsEntity> addClientApi(@RequestBody ClientsEntity client){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clientsService.addClient(client.getCompleteName(), client.getPhone(),
                            client.getEmail(), client.getAddress()));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ClientsEntity> updateClientApi(@RequestBody ClientsEntity client, @PathVariable("id") final int id){
        if(clientsService.getClientById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(clientsService.updateClientById(client.getId(), client.getCompleteName(), client.getPhone(),
                            client.getEmail(), client.getAddress()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No client found.");
        }
    }

    @DeleteMapping(path ="/{id}", produces = "application/json")
    public void deleteClientApi(@PathVariable("id") final int id){
        Optional<ClientsEntity> optionalClient = clientsService.getClientById(id);
        if(optionalClient.isPresent()){
            clientsService.deleteClientById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No client found.");
        }
    }

}
