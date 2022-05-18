package com.example.workflow.service.impl;

import com.example.workflow.model.Speaker;
import com.example.workflow.repository.SpeakerRepository;
import com.example.workflow.service.RatingSystemService;
import org.springframework.stereotype.Service;

@Service
public class RatingSystemServiceImpl implements RatingSystemService {
    private SpeakerRepository speakerRepository;

    public RatingSystemServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @Override
    public void calculateAndSetParticipationRate(Speaker speaker) {
        speaker.setRate(speaker.getRate() + 10);
        speakerRepository.save(speaker);
    }
}
