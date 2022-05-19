package com.example.workflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table
@NoArgsConstructor
@Data
public class Conference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Timestamp start;
    private Timestamp finish;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;


    public Conference(String name, String description, Timestamp start, Timestamp finish, Place place){
        this.name=name;
        this.description=description;
        this.start=start;
        this.finish=finish;
        this.place=place;
    }
}
