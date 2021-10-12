package com.formation.m2i.hotels.controllers.api;

import com.formation.m2i.hotels.model.entities.UsersEntity;
import com.formation.m2i.hotels.model.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private UsersService usersService;

    @GetMapping(path = "", produces = "application/json")
    public List<UsersEntity> getAllUsersApi(){
        return usersService.getAllUsers();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public UsersEntity getUserById(@PathVariable("id") final int id){
        Optional<UsersEntity> optionalUser = usersService.getUserById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found.");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<UsersEntity> addUserApi(@RequestBody UsersEntity user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usersService.addUser(user.getUsername(), user.getPassword(), user.getRole()));
    }

    @PostMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UsersEntity> updateUserApi(@RequestBody UsersEntity user, @PathVariable("id") final int id){
        if(usersService.getUserById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(usersService.updateUserById(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
    }

    @DeleteMapping(path ="/{id}", produces = "application/json")
    public void deleteUserApi(@PathVariable("id") final int id){
        Optional<UsersEntity> optionalUser = usersService.getUserById(id);
        if(optionalUser.isPresent()){
            usersService.deleteUserById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
    }
}
