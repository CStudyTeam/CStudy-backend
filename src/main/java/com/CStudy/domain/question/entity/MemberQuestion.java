package com.CStudy.domain.question.entity;

import com.CStudy.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_success")
    private int success;

    @Column(name = "question_fail")
    private int fail;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public MemberQuestion(
            int success,
            int fail,
            Member member,
            Question question
    ) {
        this.success = success;
        this.fail = fail;
        this.member = member;
        this.question = question;
    }
}
