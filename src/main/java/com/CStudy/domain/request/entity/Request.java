package com.CStudy.domain.request.entity;

import com.CStudy.domain.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.CStudy.domain.request.dto.request.UpdateRequestRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "request_flag")
    private boolean flag;

    @Column(name = "request_title")
    private String title;

    @Column(name = "request_description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Request(String title, String description, Member member){
        this.flag = false;
        this.title = title;
        this.description = description;
        this.member = member;
    }

    public void updateFlag(boolean flag){
        this.flag = flag;
    }


    public void updateRequest(UpdateRequestRequestDto updateRequestRequestDto) {
        this.title = updateRequestRequestDto.getTitle();
        this.description = updateRequestRequestDto.getDescription();
    }
}
