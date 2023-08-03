package com.CStudy.domain.notice.repository;

import com.CStudy.domain.notice.dto.request.NoticeSearchRequestDto;
import com.CStudy.domain.notice.dto.response.NoticeResponseDto;
import com.CStudy.domain.notice.dto.response.QNoticeResponseDto;
import com.CStudy.domain.notice.entitiy.Notice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.CStudy.domain.notice.entitiy.QNotice.notice;


public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<NoticeResponseDto> findNoticePage(Pageable pageable, NoticeSearchRequestDto noticeSearchRequestDto) {
        List<NoticeResponseDto> content = queryFactory
                .select(
                        new QNoticeResponseDto(
                                notice.title.as("noticeTitle"),
                                notice.content.as("noticeContent"),
                                notice.createdDate.as("createdAt")
                        )
                )
                .from(notice)
                .where(
                        noticeTitleEq(noticeSearchRequestDto.getTitle()),
                        noticeContentEq(noticeSearchRequestDto.getContent()),
                        noticeCreatedDateEq(noticeSearchRequestDto.getCreatedDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Notice> countQuery = queryFactory
                .selectFrom(notice)
                .where(
                        noticeTitleEq(noticeSearchRequestDto.getTitle()),
                        noticeContentEq(noticeSearchRequestDto.getContent()),
                        noticeCreatedDateEq(noticeSearchRequestDto.getCreatedDate())
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression noticeCreatedDateEq(LocalDateTime createdDate) {
        return null;
    }

    private BooleanExpression noticeContentEq(String content) {
        return StringUtils.hasText(content) ? notice.content.eq(content) : null;
    }

    private BooleanExpression noticeTitleEq(String title) {
        return StringUtils.hasText(title) ? notice.title.eq(title) : null;
    }
}
