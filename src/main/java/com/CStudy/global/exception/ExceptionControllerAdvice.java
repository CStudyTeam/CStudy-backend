package com.CStudy.global.exception;

import com.CStudy.global.exception.enums.ErrorCode;
import com.CStudy.global.exception.member.EmailDuplication;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
import com.CStudy.global.exception.member.NotFoundMemberId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse MethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
            ) throws IOException {
        log.error("user not found member");
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.MethodArgumentNotValidException.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundMemberId.class)
    public ErrorResponse notFoundMemberId(
            NotFoundMemberId e,
            HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundMemberId.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailDuplication.class)
    public ErrorResponse EmailDuplication(
            EmailDuplication e,
            HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundMemberId.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundMemberEmail.class)
    public ErrorResponse NotFoundMemberEmail(
            NotFoundMemberEmail e,
            HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundMemberId.getErrorCode(), e.getMessage());
    }

    private static void viewSlackLog(HttpServletRequest request) throws IOException {
        log.error(">요청 URL");
        log.error(">" + request.getRequestURI());
        log.error(">발생시간");
        log.error(">" + LocalDateTime.now());
        log.error(">HTTP Header 정보");
        log.error("{");
    }
}
