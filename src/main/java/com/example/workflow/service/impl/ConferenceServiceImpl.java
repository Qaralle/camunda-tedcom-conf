package com.example.workflow.service.impl;

import com.example.workflow.model.*;
import com.example.workflow.repository.ConferenceRepository;
import com.example.workflow.repository.ParticipationRepository;
import com.example.workflow.service.ConferenceService;
import com.example.workflow.service.PlaceService;
import com.example.workflow.service.ProfileService;

import com.example.workflow.util.TimePair;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final ParticipationRepository participationRepository;
    private final PlaceService placeService;
    private final ProfileService profileService;

    public ConferenceServiceImpl(ConferenceRepository repository, ParticipationRepository participationRepository, PlaceService placeService, ProfileService profileService) {
        this.conferenceRepository = repository;
        this.participationRepository = participationRepository;
        this.placeService = placeService;
        this.profileService = profileService;
    }

    @Override
    @SneakyThrows
    @Transactional(timeout = 10000)
    public Conference createConference(String name, String description, Timestamp start, Timestamp finish, List<Profile> profiles, Place place) throws IllegalArgumentException{
        Conference conference = new Conference(name,description,start,finish,place);

        validateConference(conference);

        if(profiles.size() == 0) throw new IllegalArgumentException("Отсутствует информация о профилях");
        profiles.forEach((i)->{
            if(!profileService.validate(i)){
                throw new IllegalArgumentException("Профиль не прошел валидацию");
            }
        });

        save(conference, profiles);

        return conference;
    }


    @SneakyThrows
    public Conference save(Conference conference,List<Profile> profiles) {
        profiles.forEach(i->{
            i.setConference(conference);
            profileService.save(i);
        });
        Conference resConf = conferenceRepository.save(conference);
        return resConf;
    }

    @Override
    public Conference addSpeakers(Conference conference, List<Speaker> speakers) {
        speakers.forEach((i)-> participationRepository.save(new Participation(i, conference)));
        return conference;
    }

    @Override
    public boolean validateConference(Conference conference) {
        if (conference.getName().isEmpty()){
            throw new IllegalArgumentException("Noway to create conference without name!");
        } else if (validateD(conference.getStart(), conference.getFinish())) {
            if(!canBeConducted(conference.getStart(), conference.getFinish(), conference.getPlace())) {
                throw new IllegalArgumentException("Chosen date or time has already been reserved for current place!");
            }
        }else {
            throw new IllegalArgumentException("The logical sequence of dates is broken!");
        }
        if (!placeService.validate(conference.getPlace())){
            throw new IllegalArgumentException("Place not valid");
        }

        return true;
    }

    @Override
    public boolean validateD(Timestamp start, Timestamp finish) {
        if (!start.toString().matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d*")) {
            return false;
        }
        if(!finish.toString().matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d*")) {
            return false;
        }

        int compareRes = start.compareTo(finish);

        if(compareRes == 0) {
            return false;
        }else if(compareRes > 0) {
            return false;
        }

        return true;
    }

    protected boolean canBeConducted(Timestamp start, Timestamp finish, Place place) {
        List<Conference> foundConf = conferenceRepository.findIntersectingConferences(start, finish);

        for (Conference conference : foundConf) {
            if (conference.getPlace().getDescription().equals(place.getDescription())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<Conference> findById(Long id) {
        return conferenceRepository.findById(id);
    }

    @Override
    public List<Conference> getUpcomingConferences() {
        return conferenceRepository.findUpcomingConferences();
    }

    @Override
    public List<TimePair> getReservedTime() {
        List<Conference> conferenceList = conferenceRepository.findAll();
        List<TimePair> timePairList = new ArrayList<>();

        conferenceList.forEach(c -> timePairList.add(new TimePair(c.getStart(), c.getFinish())));

        return timePairList;
    }

    @Override
    public String conferenceToString(Conference conference) {
        StringBuilder conf = new StringBuilder();

        conf.append(conference.getName());
        conf.append("\n");
        conf.append(conference.getDescription());
        conf.append("\n");
        conf.append(conference.getStart());
        conf.append("\n");

        return conf.toString();
    }

    @Override
    public List<Conference> getAcceptedConferencesBySpeaker(Speaker speaker) {
        return conferenceRepository.getAcceptedConferencesBySpeaker(speaker.getId());
    }


}
