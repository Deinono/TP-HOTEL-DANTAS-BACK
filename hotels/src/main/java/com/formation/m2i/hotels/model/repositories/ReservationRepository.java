package com.formation.m2i.hotels.model.repositories;

import com.formation.m2i.hotels.model.entities.ReservationsEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationsEntity, Integer> {
}
