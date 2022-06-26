package com.example.triple.event.repository;


import com.example.triple.event.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByPlaceName(String placeId);
}
