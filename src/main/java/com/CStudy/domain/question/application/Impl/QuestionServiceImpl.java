package com.CStudy.domain.question.application.Impl;

import com.CStudy.domain.choice.dto.CreateChoicesAboutQuestionDto;
import com.CStudy.domain.choice.entity.Choice;
import com.CStudy.domain.choice.repository.ChoiceRepository;
import com.CStudy.domain.question.application.QuestionService;
import com.CStudy.domain.question.dto.CreateQuestionAndCategoryRequestDto;
import com.CStudy.domain.question.dto.CreateQuestionRequestDto;
import com.CStudy.domain.question.entity.Category;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.question.repository.CategoryRepository;
import com.CStudy.domain.question.repository.QuestionRepository;
import com.CStudy.global.exception.category.NotFoundCategoryTile;
import lombok.extern.slf4j.Slf4j;
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

    private static final String COLLECT_ANSWER = "정답";

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository,
            ChoiceRepository choiceRepository
    ) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.choiceRepository = choiceRepository;
    }

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
//        choiceRepository.saveAll(choices); -> ConcurrentModificationException 발생 ( 순회 문제 발생 -> 조금 더 공부 )
        choiceRepository.saveAll(new ArrayList<>(choices));
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