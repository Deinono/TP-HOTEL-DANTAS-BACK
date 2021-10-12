package com.formation.m2i.hotels.model.repositories;

import com.formation.m2i.hotels.model.entities.ClientsEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientsRepository extends CrudRepository<ClientsEntity, Integer> {
}
