package com.example.workflow.repository;

import com.example.workflow.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query(value = "SELECT p.isAccepted FROM Invitation i join Participation p on p.id = p.id WHERE i.hash = ?1 ")
    boolean isInvitationAccepted(Long hash);
    Invitation getInvitationByParticipation_Conference_IdAndParticipation_Speaker_Id(Long conferenceId, Long speakerId);

    Invitation getFirstByHash(Long hash);

    @Override
    <S extends Invitation> S save(S entity);
}
