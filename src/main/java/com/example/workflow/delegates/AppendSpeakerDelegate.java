package com.example.workflow.delegates;

import com.example.workflow.model.Participation;
import com.example.workflow.model.Speaker;
import com.example.workflow.repository.SpeakerRepository;
import com.example.workflow.service.ParticipationService;
import com.example.workflow.service.SpeakerService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named("appendSpeaker")
public class AppendSpeakerDelegate implements JavaDelegate {
    private final SpeakerService speakerService;
    private final SpeakerRepository speakerRepository;

    public AppendSpeakerDelegate(SpeakerService speakerService, SpeakerRepository speakerRepository) {
        this.speakerService = speakerService;
        this.speakerRepository = speakerRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long conferenceId = (Long) delegateExecution.getVariable("conferenceId");
        String speakers = (String) delegateExecution.getVariable("speakers");

        List<String> speakerNameList = Arrays.asList(speakers.split(","));
        List<Speaker> speakerList = speakerNameList.stream().map(speakerRepository::getSpeakerByName).collect(Collectors.toList());

//        ObjectValue participationList = Variables.objectValue(speakerService.submitSpeakersToConf(speakerList, conferenceId)).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
        List<Participation> participationList = speakerService.submitSpeakersToConf(speakerList, conferenceId);
        delegateExecution.setVariable("participationList", participationList);
    }
}
