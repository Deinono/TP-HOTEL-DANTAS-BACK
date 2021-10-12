package com.formation.m2i.hotels.controllers.api;

import com.formation.m2i.hotels.model.entities.ReservationsEntity;
import com.formation.m2i.hotels.model.services.ReservationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.hibernate.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {

    private ReservationsService reservationsService;

    public ReservationApiController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @GetMapping(path = "", produces = "application/json")
    public List<ReservationsEntity> getAllReservationsApi(){
        return reservationsService.getAllReservations();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ReservationsEntity getReservationById(@PathVariable("id") final int id){
        Optional<ReservationsEntity> optionalReservation = reservationsService.getReservationById(id);
        if(optionalReservation.isPresent()) {
            return optionalReservation.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation found.");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<ReservationsEntity> addReservationApi(@RequestBody ReservationsEntity reservation){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservationsService.addReservation(reservation.getClient(), reservation.getHotel(),
                            reservation.getStartDate(), reservation.getEndDate(), reservation.getRoomNumber()));
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Client " + reservation.getClient().getId() + "or Hotel "+ reservation.getHotel().getId()+ "not found.");
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationsEntity> updateClientApi(@RequestBody ReservationsEntity reservation, @PathVariable("id") final int id){
        if(reservationsService.getReservationById(id).isPresent()){
            try {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(reservationsService.updateReservationById(reservation.getId(), reservation.getClient(), reservation.getHotel(),
                                reservation.getStartDate(), reservation.getEndDate(), reservation.getRoomNumber()));
            } catch (ObjectNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Client " + reservation.getClient().getId() + "or Hotel "+ reservation.getHotel().getId()+ "not found.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation found.");
        }
    }

    @DeleteMapping(path ="/{id}", produces = "application/json")
    public void deleteClientApi(@PathVariable("id") final int id){
        Optional<ReservationsEntity> optionalReservation = reservationsService.getReservationById(id);
        if(optionalReservation.isPresent()){
            reservationsService.deleteReservationById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation found.");
        }
    }
}
