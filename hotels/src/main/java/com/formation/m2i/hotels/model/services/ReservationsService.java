package com.formation.m2i.hotels.model.services;

import com.formation.m2i.hotels.model.entities.ClientsEntity;
import com.formation.m2i.hotels.model.entities.HotelsEntity;
import com.formation.m2i.hotels.model.entities.ReservationsEntity;
import com.formation.m2i.hotels.model.repositories.ReservationRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationsService {
    private final ReservationRepository reservationRepository;

    public ReservationsService(ReservationRepository reservationRepo) {
        this.reservationRepository = reservationRepo;
    }


    public List<ReservationsEntity> getAllReservations() {
        return (List<ReservationsEntity>)reservationRepository.findAll();
    }

    public Optional<ReservationsEntity> getReservationById(final int id){
        return reservationRepository.findById(id);
    }

    public ReservationsEntity setReservation(final ReservationsEntity reservation, final ClientsEntity client,
                                             final HotelsEntity hotel, final Date startDate,
                                             final Date endDate, final int roomNumber){
        reservation.setClient(client);
        reservation.setHotel(hotel);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setRoomNumber(roomNumber);
        return reservation;
    }

    @Transactional
    public ReservationsEntity saveReservation(ReservationsEntity reservation){
        return reservationRepository.save(reservation);
    }

    public ReservationsEntity addReservation(final ClientsEntity client, final HotelsEntity hotel,
                                        final Date startDate, final Date endDate,
                                        final int roomNumber){
        return saveReservation(setReservation(new ReservationsEntity(), client, hotel, startDate, endDate, roomNumber));
    }

    public ReservationsEntity updateReservationById(final int id, final ClientsEntity client,
                                                    final HotelsEntity hotel, final Date startDate,
                                                    final Date endDate, final int roomNumber) {
        Optional<ReservationsEntity> optionalReservation = getReservationById(id);
        ReservationsEntity reservation;
        if(optionalReservation.isPresent()) {
            reservation = optionalReservation.get();
            return saveReservation(setReservation(reservation, client, hotel, startDate, endDate, roomNumber));
        } else {
            throw new ObjectNotFoundException(id, "Reservation not found");
        }
    }

    @Transactional
    public void deleteReservationById(final int id) {
        Optional<ReservationsEntity> reservationToDelete = getReservationById(id);
        if(reservationToDelete.isPresent()) {
            reservationRepository.delete(reservationToDelete.get());
        } else {
            throw new ObjectNotFoundException(id, "Reservation not found");
        }
    }
}
