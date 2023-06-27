package com.CStudy.domain.question.application.Impl;

import com.CStudy.domain.choice.dto.CreateChoicesAboutQuestionDto;
import com.CStudy.domain.choice.entity.Choice;
import com.CStudy.domain.choice.repository.ChoiceRepository;
import com.CStudy.domain.question.application.MemberQuestionService;
import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.request.ChoiceAnswerRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.request.CreateQuestionRequestDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionResponseDto;
import com.CStudy.domain.question.entity.Category;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.CategoryRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.global.exception.Question.NotFoundQuestionWithChoicesAndCategoryById;
import com.CStudy.global.exception.category.NotFoundCategoryTile;
import com.CStudy.global.redis.RedisPublisher;
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final ChoiceRepository choiceRepository;
    private final MemberQuestionService memberQuestionService;
    private final RedisPublisher redisPublisher;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository,
            ChoiceRepository choiceRepository,
            MemberQuestionService memberQuestionService,
            RedisPublisher redisPublisher
    ) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.choiceRepository = choiceRepository;
        this.memberQuestionService = memberQuestionService;
        this.redisPublisher = redisPublisher;
    }

    private static final String COLLECT_ANSWER = "정답";


    /**
     * Create problems that are appropriate for the category.
     * You also create a view of the subsequent problems.
     *
     * @param requestDto the request DTO containing the question and category information
     * @throws NotFoundCategoryTile if the specified category title is not found
     */
    @Override
    @Transactional
    public void createQuestionChoice(CreateQuestionAndCategoryRequestDto requestDto) {

        List<Choice> choices = new ArrayList<>();

        String findCategoryTitle = requestDto.getCategoryRequestDto().getCategory();

        Category category = categoryRepository.findByTitle(findCategoryTitle)
                .orElseThrow(() -> new NotFoundCategoryTile(findCategoryTitle));

        Question question = createQuestion(requestDto.getCreateQuestionRequestDto(), category);

        for (CreateChoicesAboutQuestionDto choiceDto : requestDto.getCreateChoicesAboutQuestionDto()) {
            boolean answer = isCollectAnswer(choiceDto.getAnswer());

            Choice choice = createChoice(choiceDto, question, answer);

            choices.add(choice);
        }

        question.setChoices(choices);
        questionRepository.save(question);
        choiceRepository.saveAll(new ArrayList<>(choices));
    }

    /**
     * Create a problem that is appropriate for the category.
     * Create a view (list) of the following problems.
     *
     * @param requestDtos 단일 파일이 아닌 다중 문제를 List 형태로 전달
     */
    @Override
    @Transactional
    public void recursiveCreateQuestionChoice(List<CreateQuestionAndCategoryRequestDto> requestDtos) {
        for (CreateQuestionAndCategoryRequestDto requestDto : requestDtos) {
            createQuestionChoice(requestDto);
        }
    }

    @Override
    @Transactional
    public QuestionResponseDto findQuestionWithChoiceAndCategory(Long questionId) {

        Question question = questionRepository.findQuestionWithChoicesAndCategoryById(questionId)
                .orElseThrow(() -> new NotFoundQuestionWithChoicesAndCategoryById(questionId));

        return QuestionResponseDto.of(question);
    }

    /**
     * After inquiring about a single question, select the correct answer for the 4-point multiple question.
     * Save the correct or incorrect answers afterwards.
     *
     * @param loginUserDto 로그인 회원의 정보를 저장
     * @param questionId 단일 회원의 질문 아이디
     * @param choiceNumber 문제에 대한 정답을 선택을 한다.
     */
    @Override
    @Transactional
    public void choiceQuestion(LoginUserDto loginUserDto, Long questionId, ChoiceAnswerRequestDto choiceNumber) {

            Question question = questionRepository.findById(questionId)
                    .orElseThrow();

            List<Choice> choices = question.getChoices();
            choices.stream()
                    .filter(Choice::isAnswer)
                    .forEach(choice -> {
                        if (choice.getNumber() == choiceNumber.getChoiceNumber()) {
                            memberQuestionService.findMemberAndMemberQuestionSuccess(
                                    loginUserDto.getMemberId(),
                                    questionId,
                                    choiceNumber
                            );
                        } else {
                            memberQuestionService.findMemberAndMemberQuestionFail(
                                    loginUserDto.getMemberId(),
                                    questionId,
                                    choiceNumber
                            );
                        }
                    });
            redisPublisher.publish(ChannelTopic.of("ranking-invalidation"), "ranking");
    }



    /**
     * Paging the problem.
     *
     * @param searchCondition Select 조건
     * @param page 페이징 페이지
     * @param size 페이징 사이즈
     * @return questionRepository
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionPageWithCategoryAndTitle> questionPageWithCategory(
            QuestionSearchCondition searchCondition,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return questionRepository.findQuestionPageWithCategory(pageable, searchCondition);
    }

    private Question createQuestion(
            CreateQuestionRequestDto questionDto,
            Category category
    ) {
        return Question.builder()
                .title(questionDto.getQuestionTitle())
                .description(questionDto.getQuestionDesc())
                .explain(questionDto.getQuestionExplain())
                .category(category)
                .build();
    }

    private boolean isCollectAnswer(String answer) {
        return COLLECT_ANSWER.equals(answer);
    }

    private Choice createChoice(
            CreateChoicesAboutQuestionDto choiceDto,
            Question question,
            boolean isAnswer
    ) {
        return Choice.builder()
                .number(choiceDto.getNumber())
                .content(choiceDto.getContent())
                .question(question)
                .answer(isAnswer)
                .build();
    }
}