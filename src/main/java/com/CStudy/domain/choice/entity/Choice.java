package com.CStudy.domain.choice.entity;

import javax.persistence.*;

@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "choice_number")
    private int number;

    private String content;

    private boolean answer = false;


}
