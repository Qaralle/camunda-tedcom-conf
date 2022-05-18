package com.example.workflow.service;

public interface InvitationHashGeneratingService {

    Long generateHash(Long speakerId, Long conferenceId);
}
