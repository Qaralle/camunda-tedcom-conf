package com.example.workflow.service;


import com.example.workflow.model.Place;

public interface PlaceService {
    boolean validate(Place place);
    Place create(String description);
    Place save(Place place);
}
