package com.example.workflow.service.impl;

import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;
import com.example.workflow.repository.ParticipationRepository;
import com.example.workflow.service.InviteService;
import com.example.workflow.service.ParticipationService;
import com.example.workflow.service.SpeakerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class ParticipationServiceImpl implements ParticipationService {
    private final InviteService inviteService;

    private final ParticipationRepository participationRepository;
    private final SpeakerService speakerService;


    public ParticipationServiceImpl(InviteService inviteService, ParticipationRepository participationRepository, SpeakerService speakerService) {
        this.inviteService = inviteService;
        this.participationRepository = participationRepository;
        this.speakerService = speakerService;
    }

    @Override
    public List<Participation> getParticipationsForConf(Long conferenceId) {
        return participationRepository.getAllByConferenceId(conferenceId);
    }

    @Override
    public void add(Speaker speaker, Long conferenceId) throws MessagingException, IllegalArgumentException {
        Participation participation = speakerService.submitSpeakerToConf(speaker, conferenceId);
        inviteService.inviteSpeaker(participation);
    }
//
//    @Override
//    public void add(Speaker speaker, Long conferenceId) throws MessagingException {
//
//    }
}
