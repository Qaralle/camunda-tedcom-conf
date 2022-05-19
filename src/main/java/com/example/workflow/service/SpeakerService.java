package com.example.workflow.service;


import com.example.workflow.model.Conference;
import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;

import java.util.List;

public interface SpeakerService {
    Speaker createAndSaveSpeaker(String name, String email) throws IllegalArgumentException;
    List<Speaker> getAllSpeakers();
    List<Participation> submitSpeakersToConf(List<Speaker> speakers, Long conferenceId);
    Participation submitSpeakerToConf(Speaker speaker, Long conferenceId);
    List<Speaker> getAllSpeakersById(List<Long> idList);
    List<Speaker> getAllSpeakerByConference(Conference conference);
}
