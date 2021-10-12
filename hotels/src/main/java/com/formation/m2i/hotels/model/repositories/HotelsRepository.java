package com.formation.m2i.hotels.model.repositories;

import com.formation.m2i.hotels.model.entities.HotelsEntity;
import org.springframework.data.repository.CrudRepository;

public interface HotelsRepository extends CrudRepository<HotelsEntity, Integer> {
}
