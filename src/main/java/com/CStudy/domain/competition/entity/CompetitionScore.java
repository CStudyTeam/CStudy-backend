package com.CStudy.domain.competition.entity;

import com.CStudy.domain.question.entity.Question;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CompetitionScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_score_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberCompetition memberCompetition;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    private boolean success;

    @Builder
    public CompetitionScore(MemberCompetition memberCompetition, Question question){
        this.memberCompetition = memberCompetition;
        this.question = question;
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
