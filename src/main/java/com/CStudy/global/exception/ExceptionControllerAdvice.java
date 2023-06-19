package com.CStudy.global.exception;

import com.CStudy.global.exception.category.NotFoundCategoryTile;
import com.CStudy.global.exception.competition.DuplicateMemberWithCompetition;
import com.CStudy.global.exception.competition.NotFoundCompetitionId;
import com.CStudy.global.exception.competition.NotFoundMemberCompetition;
import com.CStudy.global.exception.competition.ParticipantsWereInvitedParticipateException;
import com.CStudy.global.exception.enums.ErrorCode;
import com.CStudy.global.exception.member.EmailDuplication;
import com.CStudy.global.exception.member.InvalidMatchPasswordException;
import com.CStudy.global.exception.member.NotFoundMemberEmail;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.exception.request.NotFoundRequest;
import com.CStudy.global.exception.workbook.NotFoundWorkbook;
import com.CStudy.global.exception.workbook.NotFoundWorkbookQuestion;
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
        return new ErrorResponse("400", e.getMessage());
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
    @ExceptionHandler(InvalidMatchPasswordException.class)
    public ErrorResponse InvalidMatchPasswordException(
            InvalidMatchPasswordException e,
            HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.InvalidMatchPasswordException.getErrorCode(), e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCategoryTile.class)
    public ErrorResponse NotFoundCategoryTile(
            NotFoundCategoryTile e,
            HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundCategoryTile.getErrorCode(), e.getMessage());
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
    @ExceptionHandler(NotFoundCompetitionId.class)
    public ErrorResponse NotFoundCompetitionId(
        NotFoundCompetitionId e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundCompetitionId.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundMemberCompetition.class)
    public ErrorResponse NotFoundMemberCompetition(
        NotFoundMemberCompetition e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundMemberCompetition.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParticipantsWereInvitedParticipateException.class)
    public ErrorResponse participantsWereInvitedParticipateException(
        ParticipantsWereInvitedParticipateException e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.participantsWereInvitedParticipateException.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateMemberWithCompetition.class)
    public ErrorResponse DuplicateMemberWithCompetition(
        DuplicateMemberWithCompetition e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.DuplicateMemberWithCompetition.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundRequest.class)
    public ErrorResponse NotFoundRequest(
        NotFoundRequest e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundRequest.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundWorkbook.class)
    public ErrorResponse NotFoundWorkbook(
        NotFoundWorkbook e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundWorkbook.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundWorkbookQuestion.class)
    public ErrorResponse NotFoundWorkbookQuestion(
        NotFoundWorkbookQuestion e,
        HttpServletRequest request
    ) throws IOException {
        viewSlackLog(request);
        return new ErrorResponse(ErrorCode.NotFoundWorkbookQuestion.getErrorCode(), e.getMessage());
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
