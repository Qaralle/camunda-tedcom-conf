package com.example.workflow.service.impl;

import com.example.workflow.model.Conference;
import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;
import com.example.workflow.repository.ParticipationRepository;
import com.example.workflow.repository.SpeakerRepository;
import com.example.workflow.service.ConferenceService;
import com.example.workflow.service.SpeakerService;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    private final SpeakerRepository speakerRepository;
    private final ConferenceService conferenceService;
    private final ParticipationRepository participationRepository;
    private final JdbcTemplate jdbcTemplate;

    public SpeakerServiceImpl(SpeakerRepository speakerRepository, ConferenceService conferenceService, ParticipationRepository participationRepository, JdbcTemplate jdbcTemplate) {
        this.speakerRepository = speakerRepository;
        this.conferenceService = conferenceService;
        this.participationRepository = participationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @SneakyThrows
    public Speaker createAndSaveSpeaker(String name, String email) throws IllegalArgumentException{
        try {
            validateSpeaker(name, email);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return speakerRepository.save(new Speaker(name, email));
    }
    @Override
    public List<Speaker> getAllSpeakers() {
        return speakerRepository.getAllSpeakers();
    }

    @Override
    public List<Speaker> getAllSpeakersById(List<Long> idList) {
        return speakerRepository.findAllById(idList);
    }

    @Override
    public List<Speaker> getAllSpeakerByConference(Conference conference) {
        return speakerRepository.getAllSpeakersByConference(conference);
    }

    @Override
    public List<Participation> submitSpeakersToConf(List<Speaker> speakers, Long conferenceId) {
        Optional<Conference> conference = conferenceService.findById(conferenceId);

        if (!conference.isPresent())
            throw new IllegalArgumentException("Conference id not valid");

        List<Participation> participationList = new ArrayList<>();
        speakers.forEach(s -> participationList.add(new Participation(s, conference.get())));
        return participationRepository.saveAll(participationList);
    }

    @Override
    public Participation submitSpeakerToConf(Speaker speaker, Long conferenceId) {
        Optional<Conference> conference = conferenceService.findById(conferenceId);

        if (!conference.isPresent())
            throw new IllegalArgumentException("conference id not valid");

        Participation participation = new Participation(speaker, conference.get());
        participationRepository.save(participation);
        return participation;
    }

    protected boolean validateSpeaker(String name, String email) throws IllegalArgumentException{

        try {

            if(name.trim().length() == 0) {
                throw new IllegalArgumentException("Empty speaker name");
            }

            if(email.replaceAll("\\s*","").length() == 0) {
                throw new IllegalArgumentException("Empty speaker email");
            }

            Optional<Speaker> s = speakerRepository.findSpeakerByEmail(email);

            if(s.isPresent()) {
                throw new IllegalArgumentException("Existing speaker");
            }

        }catch (NullPointerException ex) {
            throw new IllegalArgumentException("Null argument found");
        }

        return true;
    }
}
