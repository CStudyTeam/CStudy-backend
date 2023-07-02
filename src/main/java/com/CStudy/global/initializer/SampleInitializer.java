package com.CStudy.global.initializer;

import com.CStudy.domain.choice.dto.CreateChoicesAboutQuestionDto;
import com.CStudy.domain.competition.application.CompetitionScoreService;
import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto;
import com.CStudy.domain.competition.dto.request.CompetitionScoreRequestDto.CompetitionAnswerRequestDto;
import com.CStudy.domain.competition.dto.request.CreateCompetitionRequestDto;
import com.CStudy.domain.member.application.MemberService;
import com.CStudy.domain.member.dto.request.MemberSignupRequest;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.request.CategoryRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionRequestDto;
import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.MemberQuestionRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.domain.request.application.RequestService;
import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.entity.Request;
import com.CStudy.domain.request.repository.RequestRepository;
import com.CStudy.domain.workbook.application.WorkbookService;
import com.CStudy.domain.workbook.dto.request.CreateWorkbookRequestDto;
import com.CStudy.domain.workbook.dto.request.QuestionIdRequestDto;
import com.CStudy.domain.workbook.dto.request.WorkbookQuestionRequestDto;
import com.CStudy.global.util.LoginUserDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SampleInitializer {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final WorkbookService workbookService;
    private final CompetitionService competitionService;
    private final MemberCompetitionService memberCompetitionService;
    private final CompetitionScoreService competitionScoreService;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final MemberQuestionRepository memberQuestionRepository;

    @Transactional
    public void init() {
        insertMember();
        insertQuestion();
        insertRequest();
        insertWorkbook();
        insertCompetition();
    }

    @Transactional
    private void insertCompetition() {
        for (int i = 1; i <= 15; i++){
            CreateCompetitionRequestDto requestDto = new CreateCompetitionRequestDto();
            requestDto.setParticipants(20-i);
            requestDto.setCompetitionTitle("대회 제목"+i);
            requestDto.setCompetitionStart(LocalDateTime.of(2023, 5, i+10, 20, 0, 0));
            requestDto.setCompetitionEnd(LocalDateTime.of(2023, 6, i+10, 20, 0, 0));
            competitionService.createCompetition(requestDto);
            List<QuestionIdRequestDto> requestDtos = new ArrayList<>();
            for(int j = 1; j <= 10; j++){
                QuestionIdRequestDto qId = QuestionIdRequestDto.builder()
                    .id((long) j + i * 2)
                    .build();
                requestDtos.add(qId);
            }
            WorkbookQuestionRequestDto questions = WorkbookQuestionRequestDto.builder()
                .workbookId((long) i+10)
                .questionIds(requestDtos)
                .build();

            workbookService.addQuestion(questions);
            for(int j = 2; j <= 20-i; j++){
                LoginUserDto userDto = LoginUserDto.builder()
                        .memberId((long) j)
                        .build();
                memberCompetitionService.joinCompetition(userDto, (long)i);
                List<CompetitionAnswerRequestDto> questionRequestDtos = new ArrayList<>();
                for (int k = 1; k <= 10; k+=2){
                    CompetitionAnswerRequestDto questionDto = CompetitionAnswerRequestDto.builder()
                            .questionId((long) i * 2 + k)
                            .choiceNumber(j + k < 13 ? 1 : 2)
                            .build();
                    questionRequestDtos.add(questionDto);
                }
                for (int k = 2; k <= 10; k+=2){
                    CompetitionAnswerRequestDto questionDto = CompetitionAnswerRequestDto.builder()
                        .questionId((long) i * 2 + k)
                        .choiceNumber(k-j < 7 ? 1 : 3)
                        .build();
                    questionRequestDtos.add(questionDto);
                }
                CompetitionScoreRequestDto scoreRequestDto = CompetitionScoreRequestDto.builder()
                        .competitionId((long) i)
                        .endTime(LocalDateTime.of(2023, 6, i, 21, j, 0))
                        .questions(questionRequestDtos)
                        .build();
                competitionScoreService.scoring(scoreRequestDto, userDto);
            }
        }
    }

    @Transactional
    private void insertWorkbook() {
        for(int i = 1; i <= 10; i++) {
            CreateWorkbookRequestDto requestDto = CreateWorkbookRequestDto.builder()
                    .title("문제집 제목" + i)
                    .description("문제집 설명" + i)
                    .build();
            workbookService.createWorkbook(requestDto);
            List<QuestionIdRequestDto> requestDtos = new ArrayList<>();
            for(int j = 1; j <= 17; j++){
                QuestionIdRequestDto qId = QuestionIdRequestDto.builder()
                    .id((long) j + i * 2)
                    .build();
                requestDtos.add(qId);
            }
            WorkbookQuestionRequestDto questions = WorkbookQuestionRequestDto.builder()
                .workbookId((long) i)
                .questionIds(requestDtos)
                .build();
            workbookService.addQuestion(questions);
        }

    }

    @Transactional
    private void insertRequest() {
        for (int i = 1; i <= 30; i++) {
            CreateRequestRequestDto requestDto = CreateRequestRequestDto.builder()
                    .description("문제 요청" + i)
                    .title("요청 제목" + i)
                    .build();
            requestService.createRequest(requestDto, (long)i%3+2);
            Request request = requestRepository.findById((long) i)
                    .orElseThrow(RuntimeException::new);
            if((i > 5 && i%2 == 1) || i > 20){
                request.updateFlag(true);
            }
        }
    }

    @Transactional
    private void insertQuestion() {
        for(int i = 1; i <= 50; i++){
            CreateQuestionRequestDto questionRequestDto = CreateQuestionRequestDto.builder()
                    .questionTitle("문제 제목" + i)
                    .questionExplain("문제 해설" + i)
                    .questionDesc("문제 내용" + i)
                    .build();
            CategoryRequestDto category = CategoryRequestDto.builder()
                    .category("운영체제")
                    .build();
            if(i%5==1 || i%5==4) category.setCategory("자바");
            else if(i%5==2) category.setCategory("네트워크");
            else if(i%5==0) category.setCategory("데이터베이스");
            List<CreateChoicesAboutQuestionDto> choices = new ArrayList<>();
            for(int j = 1; j <= 5; j++){
                CreateChoicesAboutQuestionDto choice = CreateChoicesAboutQuestionDto.builder()
                        .number(j)
                        .content("보기" + j + "번")
                        .build();
                if(j == 1) {
                    choice.setAnswer("정답");
                }
                choices.add(choice);
            }

            CreateQuestionAndCategoryRequestDto createQuestionAndCategoryRequestDto
                    = new CreateQuestionAndCategoryRequestDto(questionRequestDto, category, choices);
            questionService.createQuestionChoice(createQuestionAndCategoryRequestDto);
            for(int j = i%4+2; j <= 30; j+=4){
                Question question = questionRepository.findById((long)i)
                        .orElseThrow(() -> new RuntimeException());
                Member member = memberRepository.findById((long)j)
                        .orElseThrow(() -> new RuntimeException());
                MemberQuestion memberQuestion = null;
                if(i%5==1) {
                    memberQuestion = MemberQuestion.builder()
                            .question(question)
                            .fail(2)
                            .success(0)
                            .member(member)
                            .build();
                } else {
                    memberQuestion = MemberQuestion.builder()
                            .question(question)
                            .fail(0)
                            .success(1)
                            .member(member)
                            .build();
                }
                memberQuestionRepository.save(memberQuestion);
            }
        }
    }

    @Transactional
        private void insertMember(){
            for(int i = 2; i <= 30; i++){
                MemberSignupRequest member = MemberSignupRequest.builder()
                        .email("test" + i + "@gmail.com")
                        .name("테스트유저" + i)
                        .password("test1234!")
                        .build();
                memberService.signUp(member);
            }
        }
}
