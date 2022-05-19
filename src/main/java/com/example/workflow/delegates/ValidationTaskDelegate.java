package com.example.workflow.delegates;

import com.example.workflow.model.Conference;
import com.example.workflow.model.Place;
import com.example.workflow.model.Profile;
import com.example.workflow.service.ConferenceService;
import com.example.workflow.service.ProfileService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.ArrayList;


@Named("validateData")
public class ValidationTaskDelegate implements JavaDelegate {

    private final ConferenceService conferenceService;
    private final ProfileService profileService;

    public ValidationTaskDelegate(ConferenceService conferenceService, ProfileService profileService) {
        this.conferenceService = conferenceService;
        this.profileService = profileService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String conference_name = (String) delegateExecution.getVariable("conference_name");
        String conference_description = (String) delegateExecution.getVariable("conference_description");
        Timestamp start_time = Timestamp.valueOf((String)delegateExecution.getVariable("conference_start_time"));
        Timestamp end_time = Timestamp.valueOf((String) delegateExecution.getVariable("conference_end_time"));
        String conference_place = (String) delegateExecution.getVariable("conference_place_description");
        String profile_name = (String) delegateExecution.getVariable("profile_name");
        Double profile_price = Double.valueOf((String) delegateExecution.getVariable("profile_price"));

        boolean isValid = true;
        try {
            isValid = conferenceService.validateConference(new Conference(conference_name, conference_description, start_time, end_time, new Place(conference_place)));
            isValid &= profileService.validate(new Profile(profile_name, profile_price, new ArrayList<>()));
        }catch (IllegalArgumentException ex) {
            throw new BpmnError("ValidationTRErr", ex.getMessage());
        }
    }
}
