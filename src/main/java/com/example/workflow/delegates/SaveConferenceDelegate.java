package com.example.workflow.delegates;

import com.example.workflow.model.Conference;
import com.example.workflow.model.Option;
import com.example.workflow.model.Place;
import com.example.workflow.model.Profile;
import com.example.workflow.service.ConferenceService;
import com.example.workflow.service.OptionService;
import com.example.workflow.service.PlaceService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Named("saveConference")
public class SaveConferenceDelegate implements JavaDelegate {

    private final ConferenceService conferenceService;
    private final PlaceService placeService;
    private final OptionService optionService;

    public SaveConferenceDelegate(ConferenceService conferenceService, PlaceService placeService, OptionService optionService) {
        this.conferenceService = conferenceService;
        this.placeService = placeService;
        this.optionService = optionService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {

            String conference_name = (String) delegateExecution.getVariable("conference_name");
            String conference_description = (String) delegateExecution.getVariable("conference_description");
            Timestamp start_time = Timestamp.valueOf((String) delegateExecution.getVariable("conference_start_time"));
            Timestamp end_time = Timestamp.valueOf((String) delegateExecution.getVariable("conference_end_time"));
            String conference_place = (String) delegateExecution.getVariable("conference_place_description");
            String profile_name = (String) delegateExecution.getVariable("profile_name");
            Double profile_price = Double.valueOf((String) delegateExecution.getVariable("profile_price"));

            List<Option> optionList = delegateExecution.getVariableNames().stream()
                    .filter((n) -> n.contains("option"))
                    .map(delegateExecution::getVariable)
                    .filter(n->!((String)n).isEmpty())
                    .map((n) -> new Option((String) n, new ArrayList<>()))
                    .collect(Collectors.toList());
            Profile profile = new Profile(profile_name, profile_price, optionList);
            optionList.forEach(optionService::save);
            Conference conference = new Conference(conference_name, conference_description, start_time, end_time, placeService.save(placeService.create(conference_place)));
            Conference res_conference = conferenceService.save(conference, Arrays.asList(profile));
            delegateExecution.setVariable("conferenceId", res_conference.getId());
        }catch (Exception ex) {
            throw new BpmnError("ivitationErr", ex.getMessage());
        }
    }
}
