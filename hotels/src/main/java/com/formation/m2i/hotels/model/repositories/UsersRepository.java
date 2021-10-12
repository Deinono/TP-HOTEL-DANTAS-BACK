package com.formation.m2i.hotels.model.repositories;

import com.formation.m2i.hotels.model.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {
    UsersEntity findByUsername(String s);
}
