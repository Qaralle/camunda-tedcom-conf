package com.example.workflow.delegates;

import com.example.workflow.model.Invitation;
import com.example.workflow.model.Participation;
import com.example.workflow.service.InviteService;
import com.example.workflow.service.ParticipationService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named("inviteSpeaker")
public class InviteSpeakerDelegate implements JavaDelegate {
    private final InviteService inviteService;
    private final ParticipationService participationService;

    public InviteSpeakerDelegate(InviteService inviteService, ParticipationService participationService) {
        this.inviteService = inviteService;
        this.participationService = participationService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            List<Participation> participationList = (List<Participation>) delegateExecution.getVariable("participationList");
            List<Invitation> invitationList = participationList.stream().map(inviteService::generateInvite).collect(Collectors.toList());

            ObjectValue invitationListObject = Variables.objectValue(invitationList).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
            invitationList.forEach(inviteService::save);
            delegateExecution.setVariable("invitationList", invitationListObject);
        }catch (Exception ex) {
            throw new BpmnError("ivitationErr", ex.getMessage());
        }
    }
}
