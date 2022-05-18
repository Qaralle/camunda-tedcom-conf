package com.example.workflow.service;


import com.example.workflow.model.Speaker;

public interface RatingSystemService {
    void calculateAndSetParticipationRate(Speaker speaker);
}
