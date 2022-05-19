package com.example.workflow.repository;

import com.example.workflow.model.Conference;
import com.example.workflow.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    @Query(value = "SELECT s FROM Speaker s")
    List<Speaker> getAllSpeakers();

    Speaker getSpeakerByName(String names);

    @Query(value = "SELECT s FROM Participation p join Speaker s on p.speaker = s where p.conference = :conference and p.isAccepted = true")
    List<Speaker> getAllSpeakersByConference(@Param("conference") Conference conference);

    @Override
    List<Speaker> findAllById(Iterable<Long> longs);

    Optional<Speaker> findSpeakerByEmail(String email);
}
