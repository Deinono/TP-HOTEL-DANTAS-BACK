package com.formation.m2i.hotels.model.security;

import com.formation.m2i.hotels.model.entities.UsersEntity;
import com.formation.m2i.hotels.model.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailsServiceImplementation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UsersEntity user = usersRepository.findByUsername(s);
        return new UserDetailsImplementation(user);
    }
}
