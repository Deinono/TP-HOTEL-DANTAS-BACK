package com.formation.m2i.hotels.controllers.api;

import com.formation.m2i.hotels.model.entities.UsersEntity;
import com.formation.m2i.hotels.model.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {
    @Autowired
    UsersRepository userRepository;

    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();


    @PostMapping(path = "", produces = "application/json")
    ResponseEntity<UsersEntity> checkLogin(@RequestBody UsersEntity userv ){
        System.out.println( userv.getUsername() );
        try{
            UsersEntity user = userRepository.findByUsername( userv.getUsername() );

            if( user != null && passwordEcorder.matches( userv.getPassword() , user.getPassword()) ){
                user.setPassword("");
                return ResponseEntity.ok() // ok => 200
                        .body(user);
            }else{
                throw new Exception( "Login ou password incorrect" );
            }
        }catch ( Exception e ){
            System.out.println("Je suis ici");
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() ); // KO : 400
        }
    }
}
