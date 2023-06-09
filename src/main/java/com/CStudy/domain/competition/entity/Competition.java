package com.CStudy.domain.competition.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id")
    private Long id;

    @Column(name = "Competition_title")
    private String competitionTitle;

    @Column(name = "competition_participants")
    private int participants;

    private LocalDateTime competitionStart;

    private LocalDateTime competitionEnd;


    @OneToMany(
            mappedBy = "competition",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    Set<MemberCompetition> memberCompetitionSet = new HashSet<>();

    @Builder
    public Competition(String competitionTitle, int participants, LocalDateTime competitionStart, LocalDateTime competitionEnd) {
        this.competitionTitle = competitionTitle;
        this.participants = participants;
        this.competitionStart = competitionStart;
        this.competitionEnd = competitionEnd;
    }

    public void decreaseParticipantsCount() {
        this.participants--;
    }
}
