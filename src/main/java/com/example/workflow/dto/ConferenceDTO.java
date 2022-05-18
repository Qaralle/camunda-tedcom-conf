package com.example.workflow.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConferenceDTO {
    private String name;
    private String description;
    private Timestamp start;
    private Timestamp finish;

    private List<String> allOptions = new ArrayList<>();
    private List<ProfileDTO> profiles  = new ArrayList<>();

    private String place_description;
}
