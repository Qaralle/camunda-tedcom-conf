package com.example.workflow.service;



import com.example.workflow.dto.ProfileDTO;
import com.example.workflow.model.Conference;
import com.example.workflow.model.Option;
import com.example.workflow.model.Profile;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    boolean validate(Profile profile);
    Profile create(String name, Double price, List<Option> options);
    List<Profile> findAllByConference(Conference conference);
    void save(Profile profile);
    List<Profile> crateList(List<ProfileDTO> raws, Map<String, Option> options);
}
