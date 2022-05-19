package com.example.workflow.repository;

import com.example.workflow.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> getAllByConferenceId(Long conferenceId);
}
