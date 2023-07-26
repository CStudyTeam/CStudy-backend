package com.CStudy.domain.comment.application;

import com.CStudy.domain.comment.dto.request.NoticeCommentRequestDto;
import com.CStudy.domain.comment.entitiy.Comment;
import com.CStudy.domain.comment.repository.CommentRepository;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.notice.entitiy.Notice;
import com.CStudy.domain.notice.repository.NoticeRepository;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    public CommentServiceImpl(CommentRepository commentRepository, NoticeRepository noticeRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.noticeRepository = noticeRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void saveNoticeComment(NoticeCommentRequestDto dto, LoginUserDto loginUserDto) {
        try {
            Notice notice = noticeRepository.getReferenceById(dto.getNoticeId());
            Member member = memberRepository.findById(loginUserDto.getMemberId()).orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));
            Comment noticeComment = dto.toEntity(notice, member);

            if (dto.getParentCommentId() != null) {
                Comment parentComment = commentRepository.getReferenceById(dto.getParentCommentId());
                parentComment.addChildComment(noticeComment);
            } else {
                commentRepository.save(noticeComment);
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }
}
