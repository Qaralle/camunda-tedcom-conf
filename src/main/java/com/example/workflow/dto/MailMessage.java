package com.example.workflow.dto;

import lombok.Data;

@Data
public class MailMessage {
    private String email;
    private String text;
    private boolean isHTML;
}