package com.formation.m2i.hotels.model.configuration;

import com.formation.m2i.hotels.model.entities.UsersEntity;
import com.formation.m2i.hotels.model.repositories.UsersRepository;
import com.formation.m2i.hotels.model.security.ApplicationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private final ApplicationConfig app;

    public LoadDatabase(ApplicationConfig app) {
        this.app = app;
    }


    /**
     * Create an administrator account in the database if the table User is empty.
     */
    @Bean
    public CommandLineRunner initDatabase(UsersRepository usersRepository){

        return args -> {
            if(usersRepository.count() == 0){
                usersRepository.save(new UsersEntity("admin", app.encodePassword("admin"),"ROLE_ADMIN"));
            }
        };
    }
}
