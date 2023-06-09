package com.CStudy.domain.workbook.dto.response;

import com.CStudy.domain.workbook.entity.Workbook;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkbookResponseDto {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public static WorkbookResponseDto of(Workbook workbook){
        return WorkbookResponseDto.builder()
                .id(workbook.getId())
                .title(workbook.getTitle())
                .description(workbook.getDescription())
                .createdAt(workbook.getCreatedAt())
                .build();
    }
}
