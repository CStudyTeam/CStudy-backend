package com.CStudy.domain.member.application.impl.duplicate;

import com.CStudy.domain.member.application.DuplicateService;
import com.CStudy.domain.member.dto.response.DuplicateResponseDto;
import com.CStudy.domain.member.dto.typeEnum.DuplicateResult;
import com.CStudy.domain.member.dto.typeEnum.DuplicateType;
import com.CStudy.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements DuplicateService {

    private final MemberRepository memberRepository;

    public EmailServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public DuplicateResponseDto signupDivisionDuplicateCheck(String type, String value) {
        return DuplicateResponseDto.of(memberRepository.findByEmail(value)
                .isPresent() ? DuplicateResult.FALSE.getDivisionResult() : DuplicateResult.TRUE.getDivisionResult());
    }

    @Override
    public DuplicateType getType() {
        return DuplicateType.email;
    }
}
