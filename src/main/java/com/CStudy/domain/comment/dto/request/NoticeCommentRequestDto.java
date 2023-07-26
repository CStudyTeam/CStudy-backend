package com.CStudy.domain.comment.dto.request;

import com.CStudy.domain.comment.entitiy.Comment;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.notice.entitiy.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCommentRequestDto {
    private Long noticeId;
    private Long parentCommentId;
    private String content;

    public Comment toEntity(Notice notice, Member member) {
        return Comment.of(
                notice,
                member,
                this.content
        );
    }
}
