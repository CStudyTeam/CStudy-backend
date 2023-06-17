package com.CStudy.domain.workbook.entity;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.question.entity.Question;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WorkbookQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workbook_question_id")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "workbook_id")
    private Workbook workbook;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public WorkbookQuestion(Workbook workbook, Question question){
        this.workbook = workbook;
        this.question = question;
    }

}
