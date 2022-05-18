package com.example.workflow.service.impl;


import com.example.workflow.model.Conference;
import com.example.workflow.model.Speaker;
import com.example.workflow.service.ConferenceService;
import com.example.workflow.service.MailService;
import com.example.workflow.service.NotificationService;
import com.example.workflow.service.SpeakerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final SpeakerService speakerService;
    private final ConferenceService conferenceService;
    private final MailService mailService;

    public NotificationServiceImpl(SpeakerService speakerService,ConferenceService conferenceService,MailService mailService) {
        this.speakerService = speakerService;
        this.conferenceService = conferenceService;
        this.mailService = mailService;
    }

    @Override
    @Scheduled(cron="0 07 19 * * *", zone="Europe/Moscow")
    public void eveningNotification() {
        List<Conference> upcomingConferences = conferenceService.getUpcomingConferences();
        Set<Speaker> mustBeNotified = new HashSet<>();
        upcomingConferences.forEach(c->mustBeNotified.addAll(speakerService.getAllSpeakerByConference(c)));

        mustBeNotified.forEach( s -> prepareListOfConference(s,conferenceService.getAcceptedConferencesBySpeaker(s)));
    }


    private void prepareListOfConference(Speaker speaker, List<Conference> conferences)  {
//        StringBuilder msgText = new StringBuilder();
//
//        msgText.append("У вас есть предстоящие конференции на завтра:\n");
//        conferences.forEach(c->msgText.append(conferenceService.conferenceToString(c)));
//        msgText.append("Fuck you, "+speaker.getName());
//
//
//        mailService.sendMail(speaker.getEmail(),msgText.toString());
    }
}
