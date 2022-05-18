package com.example.workflow.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ProfileDTO {
    private String name;
    private Double price;

    private Map<String,Boolean> options = new HashMap<>();
}
