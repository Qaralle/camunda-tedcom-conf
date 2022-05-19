package com.example.workflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@NoArgsConstructor
public class Speaker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Integer rate;

    public Speaker(String name, String email){
        this.email = email;
        this.name = name;
        this.rate = 0;
    }

    public Speaker(String name, String email, int rate){
        this.email = email;
        this.name = name;
        this.rate = rate;
    }
}
