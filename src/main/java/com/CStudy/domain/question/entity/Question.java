package com.CStudy.domain.question.entity;

import com.CStudy.domain.choice.entity.Choice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_title")
    private String title;

    @Column(name = "question_description")
    private String description;

    @Column(name = "question_explain")
    private String explain;

    @OneToMany(
            mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    Set<MemberQuestion> questions = new HashSet<>();

    @OneToMany(
            mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<Choice>choices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Question(Long id, String title, String description, String explain, Set<MemberQuestion> questions, List<Choice> choices, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.explain = explain;
        this.questions = questions;
        this.choices = choices;
        this.category = category;
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

}
