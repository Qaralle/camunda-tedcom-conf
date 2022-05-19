package com.example.workflow.service;


import com.example.workflow.model.Invitation;
import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;

import javax.mail.MessagingException;
import java.util.List;

public interface InviteService {
    void inviteSpeaker(Participation participation) throws MessagingException;
    boolean inviteList(List<Speaker> speakers, Long conferenceId) throws MessagingException;
    Invitation generateInvite(Participation participation);
    boolean isInvited(Long speakerId, Long conferenceId);
    boolean isInvited(Long hash);

    Invitation save(Invitation invitation);
}
