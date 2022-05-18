package com.example.workflow.service;


import com.example.workflow.model.Speaker;

import javax.mail.MessagingException;

public interface ParticipationService {
    void add(Speaker speaker, Long conferenceId) throws MessagingException;
}
