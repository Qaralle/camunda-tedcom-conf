package com.example.workflow.service;


import com.example.workflow.model.Conference;
import com.example.workflow.model.Speaker;

import javax.mail.MessagingException;

public interface InvitationConfirmationService {
    void acceptInvitation(Long hash) throws MessagingException;
    void sendAdditionalInformation(Speaker speaker, Conference conference) throws MessagingException;

    boolean isInvitationAccepted(Long hash);
}
