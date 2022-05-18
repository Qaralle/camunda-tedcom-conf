package com.example.workflow.service;

public interface MailService {
    void sendMail(String email, String text);
    void sendMail(String email, String text, boolean isHTML);
}
