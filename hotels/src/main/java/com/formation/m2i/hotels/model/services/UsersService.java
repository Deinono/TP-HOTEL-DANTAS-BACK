package com.formation.m2i.hotels.model.services;

import com.formation.m2i.hotels.model.entities.ClientsEntity;
import com.formation.m2i.hotels.model.entities.UsersEntity;
import com.formation.m2i.hotels.model.repositories.UsersRepository;
import org.hibernate.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepo) {
        this.usersRepository = usersRepo;
    }

    public List<UsersEntity> getAllUsers() {
        return (List<UsersEntity>)usersRepository.findAll();
    }

    public Optional<UsersEntity> getUserById(final int id){
        return usersRepository.findById(id);
    }

    public UsersEntity setUser(final UsersEntity user, final String username,
                                   final String password, final String role){

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @Transactional
    public UsersEntity saveUser(UsersEntity user){
        return usersRepository.save(user);
    }

    public UsersEntity addUser(final String username, final String password, final String role){
        return saveUser(setUser(new UsersEntity(), username, password, role));
    }

    public UsersEntity updateUserById(final int id,final String username,
                                      final String password, final String role) {
        Optional<UsersEntity> optionalUser = getUserById(id);
        UsersEntity user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            return saveUser(setUser(user, username, password, role));
        } else {
            throw new ObjectNotFoundException(id, "User not found");
        }
    }

    @Transactional
    public void deleteUserById(final int id) {
        Optional<UsersEntity> userToDelete = getUserById(id);
        if(userToDelete.isPresent()) {
            usersRepository.delete(userToDelete.get());
        } else {
            throw new ObjectNotFoundException(id, "User not found");
        }
    }
}
