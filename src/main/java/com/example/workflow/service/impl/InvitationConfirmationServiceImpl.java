package com.example.workflow.service.impl;

import com.example.workflow.model.Conference;
import com.example.workflow.model.Invitation;
import com.example.workflow.model.Speaker;
import com.example.workflow.repository.InvitationRepository;
import com.example.workflow.service.InvitationConfirmationService;
import com.example.workflow.service.MailService;
import com.example.workflow.service.RatingSystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.SQLException;

@Service
public class InvitationConfirmationServiceImpl implements InvitationConfirmationService {
    private final RatingSystemService ratingSystemService;
    private final InvitationRepository invitationRepository;
    private final MailService mailService;

    public InvitationConfirmationServiceImpl(RatingSystemService ratingSystemService, InvitationRepository invitationRepository, MailService mailService) {
        this.ratingSystemService = ratingSystemService;
        this.invitationRepository = invitationRepository;
        this.mailService = mailService;
    }

    @Override
    public void acceptInvitation(Long hash) throws MessagingException{
        Invitation invitation = invitationRepository.getFirstByHash(hash);
        invitation.getParticipation().setIsAccepted(true);
        invitationRepository.save(invitation);
        Speaker speaker = invitation.getParticipation().getSpeaker();
        ratingSystemService.calculateAndSetParticipationRate(speaker);
        try {
            sendAdditionalInformation(speaker, invitation.getParticipation().getConference());
        }catch (MessagingException ex) {
            throw new MessagingException("Unable to send additional information.\n Try to accept invite again.");
        }
    }

    @Override
    public void sendAdditionalInformation(Speaker speaker, Conference conference) throws MessagingException{
        String text = "extra information place holder";
        mailService.sendMail(speaker.getEmail(), text);
    }

    @Override
    public boolean isInvitationAccepted(Long hash) {
        return invitationRepository.isInvitationAccepted(hash);
    }
}
