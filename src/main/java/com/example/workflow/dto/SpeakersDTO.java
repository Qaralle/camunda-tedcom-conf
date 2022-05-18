package com.example.workflow.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpeakersDTO {
    public Long conferenceId;
    public List<Long> speakerIdList = new ArrayList<>();
}
