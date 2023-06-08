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
import com.CStudy.global.util.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository,
            ChoiceRepository choiceRepository,
            MemberQuestionService memberQuestionService
    ) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.choiceRepository = choiceRepository;
        this.memberQuestionService = memberQuestionService;
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

    @Override
    @Transactional
    public void recursiveCreateQuestionChoice(List<CreateQuestionAndCategoryRequestDto> requestDtos) {
        for (CreateQuestionAndCategoryRequestDto requestDto : requestDtos) {
            createQuestionChoice(requestDto);
        }
    }

    @Override
    public QuestionResponseDto findQuestionWithChoiceAndCategory(Long questionId) {

        Question question = questionRepository.findQuestionWithChoicesAndCategoryById(questionId)
                .orElseThrow(() -> new NotFoundQuestionWithChoicesAndCategoryById(questionId));

        return QuestionResponseDto.of(question);
    }

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
                                choiceNumber.getChoiceNumber()
                        );
                    } else {
                        memberQuestionService.findMemberAndMemberQuestionFail(
                                loginUserDto.getMemberId(),
                                questionId,
                                choiceNumber.getChoiceNumber()
                        );
                    }
                });
    }

    @Override
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