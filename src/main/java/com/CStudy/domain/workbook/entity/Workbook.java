package com.CStudy.domain.workbook.entity;

import com.CStudy.domain.competition.entity.Competition;
import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.workbook.dto.request.UpdateWorkbookRequestDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Workbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workbook_id")
    private Long id;

    @Column(name = "workbook_title")
    private String title;

    @Column(name = "workbook_description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "competition_end_time")
    private LocalDateTime competitionEndTime;

    @OneToMany(
            mappedBy = "workbook",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<WorkbookQuestion> questions = new ArrayList<>();

    @OneToOne(
        mappedBy = "workbook",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private Competition competition;

    @Builder
    public Workbook(String title, String description, LocalDateTime endTime){
        this.title = title;
        this.description = description;
        this.competitionEndTime = LocalDateTime.now();
    }

    public void addQuestion(WorkbookQuestion question){
        this.questions.add(question);
    }

    public void setCompetition(Competition competition){
        this.competition = competition;
        this.competitionEndTime = competition.getCompetitionEnd();
    }

    public void changeWorkbook(UpdateWorkbookRequestDto workbookDto) {
        this.title = workbookDto.getTitle();
        this.description = workbookDto.getDescription();
    }

    public void deleteQuestion(WorkbookQuestion question) {
        this.questions.remove(question);
    }
}
