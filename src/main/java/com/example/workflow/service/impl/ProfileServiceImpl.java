package com.example.workflow.service.impl;

import com.example.workflow.dto.ProfileDTO;
import com.example.workflow.model.Conference;
import com.example.workflow.model.Option;
import com.example.workflow.model.Profile;
import com.example.workflow.repository.ProfileRepository;
import com.example.workflow.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validate(Profile profile) {

        if(profile.getName().trim().length() == 0) {
            return false;
        }else if(profile.getPrice() < 0) {
            return false;
        }else if(profile.getOptions().size() == 0) {
            return false;
        }

        return true;
    }

    @Override
    public Profile create(String name, Double price, List<Option> options) {
        return new Profile(name,price,options);
    }

    @Override
    public List<Profile> findAllByConference(Conference conference) {
        return repository.findAllByConference(conference);
    }

    @Override
    public void save(Profile profile) {
        repository.save(profile);
    }

    @Override
    public List<Profile> crateList(List<ProfileDTO> raws, Map<String, Option> options) {
        List<Profile> profiles = new ArrayList<>();

        raws.forEach(i-> {
            List<Option> concreteOptions = new ArrayList<>();
            for(Map.Entry<String, Boolean> entry: i.getOptions().entrySet()) {
                concreteOptions.add(options.get(entry.getKey()));
            }
            profiles.add(create(i.getName(),
                    i.getPrice(), concreteOptions));
        });

        return profiles;
    }


}
