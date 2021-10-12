package com.formation.m2i.hotels.model.services;

import com.formation.m2i.hotels.model.entities.HotelsEntity;
import com.formation.m2i.hotels.model.repositories.HotelsRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class HotelsService {

    private final HotelsRepository hotelsRepository;

    public HotelsService(HotelsRepository hotelsRep) {
        this.hotelsRepository = hotelsRep;
    }

    public List<HotelsEntity> getAllHotels() {
        return (List<HotelsEntity>)hotelsRepository.findAll();
    }

    public Optional<HotelsEntity> getHotelById(final int id) {
        return hotelsRepository.findById(id);
    }

    public HotelsEntity setHotel(final HotelsEntity hotel, final String name,
                                 final int starNumber, final String address,
                                 final String phone, final String email,
                                 final String city) {
        hotel.setName(name);
        hotel.setStarNumber(starNumber);
        hotel.setAddress(address);
        hotel.setPhone(phone);
        hotel.setEmail(email);
        hotel.setCity(city);
        return hotel;
    }

    @Transactional
    public HotelsEntity saveHotel(final HotelsEntity hotel){
        return hotelsRepository.save(hotel);
    }

    public HotelsEntity addHotel(final String name, final int starNumber,
                                 final String address, final String phone,
                                 final String email, final String city) {
        return saveHotel(setHotel(new HotelsEntity(), name, starNumber, address, phone, email, city));
    }

    public HotelsEntity updateHotelById(final int id, final String name,
                                        final int starNumber, final String address,
                                        final String phone, final String email,
                                        final String city ){
        Optional<HotelsEntity> optionalHotel = getHotelById(id);
        HotelsEntity hotel;
        if(optionalHotel.isPresent()) {
            hotel = optionalHotel.get();
            return saveHotel(setHotel(hotel, name, starNumber,address, phone, email, city));
        } else {
            throw new ObjectNotFoundException(id, "Hotel not found");
        }
    }

    public void deleteHotelById(final int id){
        Optional<HotelsEntity> hotelToDelete = getHotelById(id);
        if(hotelToDelete.isPresent()) {
            hotelsRepository.delete(hotelToDelete.get());
        } else {
            throw new ObjectNotFoundException(id, "Hotel not found");
        }
    }
}
