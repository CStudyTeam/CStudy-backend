package com.CStudy.domain.workbook.entity;

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

    @Column(name = "")
    private LocalDateTime competitionEndTime;

    @BatchSize(size = 7)
    @OneToMany(
            mappedBy = "workbook",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<WorkbookQuestion> questions = new ArrayList<>();

    @Builder
    public Workbook(String title, String description, LocalDateTime endTime){
        this.title = title;
        this.description = description;
        if(endTime == null) {
            competitionEndTime = LocalDateTime.now();
        } else {
            competitionEndTime = endTime;
        }
    }

    public void addQuestion(WorkbookQuestion question){
        this.questions.add(question);
    }

    public void changeWorkbook(UpdateWorkbookRequestDto workbookDto) {
        this.title = workbookDto.getTitle();
        this.description = workbookDto.getDescription();
    }

    public void deleteQuestion(WorkbookQuestion question) {
        this.questions.remove(question);
    }
}
