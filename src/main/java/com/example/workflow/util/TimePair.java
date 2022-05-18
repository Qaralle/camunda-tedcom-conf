package com.example.workflow.util;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimePair {
    private Timestamp start;
    private Timestamp finish;

    public TimePair(Timestamp start, Timestamp finish) {
        this.start = start;
        this.finish = finish;
    }
}
