package com.example.workflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "participation")
public class Participation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @OneToOne(mappedBy = "participation", cascade = CascadeType.MERGE)
    private Invitation invitation;


    public Participation(Speaker speaker, Conference conference) {
        this.speaker = speaker;
        this.conference = conference;
        isAccepted = false;
    }
}

