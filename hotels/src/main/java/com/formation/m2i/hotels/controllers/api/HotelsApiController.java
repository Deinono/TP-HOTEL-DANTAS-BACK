package com.formation.m2i.hotels.controllers.api;

import com.formation.m2i.hotels.model.entities.HotelsEntity;
import com.formation.m2i.hotels.model.services.HotelsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
public class HotelsApiController {

    private HotelsService hotelsService;

    public HotelsApiController(HotelsService hotelsService) {
        this.hotelsService = hotelsService;
    }

    @GetMapping(path = "", produces = "application/json")
    public List<HotelsEntity> getAllHotelsApi(){
        return hotelsService.getAllHotels();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public HotelsEntity getHotelById(@PathVariable("id") final int id){
        Optional<HotelsEntity> optionalHotel = hotelsService.getHotelById(id);
        if(optionalHotel.isPresent()) {
            return optionalHotel.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Hotel found.");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<HotelsEntity> addHotelApi(@RequestBody HotelsEntity hotel){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelsService.addHotel(hotel.getName(),hotel.getStarNumber(), hotel.getAddress(),
                        hotel.getPhone(), hotel.getEmail(), hotel.getCity()));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<HotelsEntity> updateHotelApi(@RequestBody HotelsEntity hotel, @PathVariable("id") final int id){
        if(hotelsService.getHotelById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(hotelsService.updateHotelById(hotel.getId(), hotel.getName(),hotel.getStarNumber(), hotel.getAddress(),
                            hotel.getPhone(), hotel.getEmail(), hotel.getCity()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hotel found.");
        }
    }

    @DeleteMapping(path ="/{id}", produces = "application/json")
    public void deleteClientApi(@PathVariable("id") final int id){
        Optional<HotelsEntity> optionalHotel = hotelsService.getHotelById(id);
        if(optionalHotel.isPresent()){
            hotelsService.deleteHotelById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hotel found.");
        }
    }
}
