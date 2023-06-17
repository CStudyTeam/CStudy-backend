package com.CStudy.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public File(String fileName, Member member) {
        this.fileName = fileName;
        this.member = member;
    }
}
