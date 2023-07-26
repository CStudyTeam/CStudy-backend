package com.CStudy.domain.aop;

import com.CStudy.domain.request.dto.request.UpdateRequestRequestDto;
import com.CStudy.domain.request.repository.RequestRepository;
import com.CStudy.global.exception.request.NotMatchRequestAuth;
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RequestAuthCheckAop {

    private final RequestRepository requestRepository;

    public RequestAuthCheckAop(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    @Around("@annotation(authCheckAnnotation)")
    public Object requestAuthCheckAop(ProceedingJoinPoint joinPoint, AuthCheckAnnotation authCheckAnnotation) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length > 1 && args[1] instanceof LoginUserDto) {
            long id;
            if (args[0] instanceof Long) {
                id = (Long) args[0];
                log.info("Long Id :{}", id);
            } else if (args[0] instanceof UpdateRequestRequestDto) {
                id = ((UpdateRequestRequestDto) args[0]).getId();
                log.info("UpdateTodoRequestDto:{}", id);
            } else {
                throw new IllegalArgumentException("잘못된 메소드 인자입니다.");
            }

            LoginUserDto loginUserDto = (LoginUserDto) args[1];
            log.info("LoginUserDto:{}", loginUserDto.getMemberId());

            if (requestRepository.findByIdAndMemberId(id, loginUserDto.getMemberId()).isEmpty()) {
                log.info("권한 체크 error 발생");
                throw new NotMatchRequestAuth(id);
            }
        }
        return joinPoint.proceed();
    }
}