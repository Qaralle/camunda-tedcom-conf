package com.example.workflow.repository;


import com.example.workflow.model.Conference;
import com.example.workflow.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findAllByConference(Conference conference);
}
