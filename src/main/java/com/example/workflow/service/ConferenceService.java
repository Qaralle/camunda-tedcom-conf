package com.example.workflow.service;



import com.example.workflow.model.Conference;
import com.example.workflow.model.Place;
import com.example.workflow.model.Profile;
import com.example.workflow.model.Speaker;
import com.example.workflow.util.TimePair;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ConferenceService {
    Conference createConference(String name, String description, Timestamp start, Timestamp finish, List<Profile> profiles, Place place) throws IllegalArgumentException;
    Conference addSpeakers(Conference conference, List<Speaker> speakers);
    boolean validateD(Timestamp start, Timestamp finish);
    Optional<Conference> findById(Long id);
    List<Conference> getUpcomingConferences();
    List<TimePair> getReservedTime();
    String conferenceToString(Conference conference);
    List<Conference> getAcceptedConferencesBySpeaker(Speaker speaker);
    Conference save(Conference conference, List<Profile> profiles);
    boolean validateConference(Conference conference);
}
