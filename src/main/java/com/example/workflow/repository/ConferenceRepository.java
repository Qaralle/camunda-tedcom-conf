package com.example.workflow.repository;


import com.example.workflow.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {


    @Query(value = "SELECT c FROM Conference c WHERE (c.start >= ?1 or (c.start < ?1 and c.finish > ?1)) and ((c.finish > ?2 and c.start < ?2) or c.finish <= ?2)")
    List<Conference> findIntersectingConferences(Timestamp start, Timestamp finish);

    @Query(nativeQuery = true, value = "SELECT * FROM Conference WHERE start > CURRENT_TIMESTAMP and start < (select yestday()) ")
    List<Conference> findUpcomingConferences();

    @Query(nativeQuery = true, value = "SELECT * FROM Conference join participation on conference.id = conference_id join Speaker on speaker_id = Speaker.id where speaker_id = :speaker and is_accepted = true and start > CURRENT_TIMESTAMP and start < (select yestday())")
    List<Conference> getAcceptedConferencesBySpeaker(@Param("speaker") Long s_id);

}
