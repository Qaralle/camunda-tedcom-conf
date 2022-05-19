package com.example.workflow.delegates;

import com.example.workflow.service.SpeakerService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import javax.inject.Named;

@Named("getListOfSpeakers")
public class GetListOfSpeakersDelegate implements JavaDelegate {
    private final SpeakerService speakerService;

    public GetListOfSpeakersDelegate(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ObjectValue jsonHumans = Variables.objectValue(speakerService.getAllSpeakers()).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("speakersList", jsonHumans);
    }
}
