package com.example.workflow.repository;

import com.example.workflow.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findPlaceByDescription(String description);
}
