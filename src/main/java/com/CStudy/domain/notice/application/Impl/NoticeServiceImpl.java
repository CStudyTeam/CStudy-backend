package com.CStudy.domain.notice.application.Impl;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.notice.application.NoticeService;
import com.CStudy.domain.notice.dto.request.NoticeSaveRequestDto;
import com.CStudy.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.CStudy.domain.notice.entitiy.Notice;
import com.CStudy.domain.notice.repository.NoticeRepository;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.exception.notice.NotMatchAdminIpException;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class NoticeServiceImpl implements NoticeService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(
            MemberRepository memberRepository,
            NoticeRepository noticeRepository
    ) {
        this.memberRepository = memberRepository;
        this.noticeRepository = noticeRepository;
    }

    @Override
    @Transactional
    public void saveNotice(NoticeSaveRequestDto noticeSaveRequestDto, LoginUserDto loginUserDto) {
        noticeRepository.save(Notice.builder()
                .title(noticeSaveRequestDto.getTitle())
                .content(noticeSaveRequestDto.getContent())
                .member(memberRepository.findById(loginUserDto.getMemberId())
                        .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId())))
                .build());
    }

    @Override
    @Transactional
    public void updateNotice(NoticeUpdateRequestDto noticeUpdateRequestDto, LoginUserDto loginUserDto) {
        noticeRepository.findByTitle(noticeUpdateRequestDto.getTitle())
                .orElseThrow().updateNotice(noticeUpdateRequestDto);
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId, LoginUserDto loginUserDto) {
        Long adminId = 1L;

        Member member = memberRepository.findById(loginUserDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));

        Optional.of(member.getId())
                .filter(id -> id.equals(adminId))
                .orElseThrow(() -> new NotMatchAdminIpException(adminId));

        noticeRepository.deleteById(noticeId);
    }
}
