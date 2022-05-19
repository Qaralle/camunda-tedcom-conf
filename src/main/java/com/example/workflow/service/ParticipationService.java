package com.example.workflow.service;


import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;

import javax.mail.MessagingException;
import java.util.List;

public interface ParticipationService {
    void add(Speaker speaker, Long conferenceId) throws MessagingException;

    List<Participation> getParticipationsForConf(Long conferenceId);
}
