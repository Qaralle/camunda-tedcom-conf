package com.example.workflow.delegates;

import com.example.workflow.service.ConferenceService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import javax.inject.Named;

@Named("getReservedTime")
public class GetReservedTimeDelegate implements JavaDelegate {

    private final ConferenceService conferenceService;

    public GetReservedTimeDelegate(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution){
        ObjectValue jsonHumans = Variables.objectValue(conferenceService.getReservedTime()).serializationDataFormat("application/json").create();
        delegateExecution.setVariable("reservedTimeList", jsonHumans);
    }
}
