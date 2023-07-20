package com.CStudy.domain.question.repository;

import com.CStudy.domain.choice.dto.ChoiceQuestionResponseDto;
import com.CStudy.domain.competition.dto.response.CompetitionQuestionDto;
import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QQuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.entity.Question;
import com.CStudy.global.util.LoginUserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.CStudy.domain.choice.entity.QChoice.choice;
import static com.CStudy.domain.competition.entity.QCompetition.competition;
import static com.CStudy.domain.member.entity.QMember.member;
import static com.CStudy.domain.question.entity.QCategory.category;
import static com.CStudy.domain.question.entity.QMemberQuestion.memberQuestion;
import static com.CStudy.domain.question.entity.QQuestion.question;
import static com.CStudy.domain.workbook.entity.QWorkbook.workbook;
import static com.CStudy.domain.workbook.entity.QWorkbookQuestion.workbookQuestion;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<QuestionPageWithCategoryAndTitle> findQuestionPageWithCategory(Pageable pageable, QuestionSearchCondition questionSearchCondition, LoginUserDto loginUserDto) {

        List<QuestionPageWithCategoryAndTitle> content = queryFactory.select(
                        new QQuestionPageWithCategoryAndTitle(
                                question.id.as("questionId"),
                                question.title.as("questionTitle"),
                                category.categoryTitle.as("categoryTitle"),
                                divisionStatusAboutMemberId(loginUserDto)
                        )).from(question).distinct()
                .leftJoin(question.category, category)
                .leftJoin(question.questions, memberQuestion)
                .leftJoin(memberQuestion.member, member)
                .where(
                        questionTitleEq(questionSearchCondition.getQuestionTitle()),
                        categoryTitleEq(questionSearchCondition.getCategoryTitle()),
                        memberIdEq(questionSearchCondition.getMemberId()),
                        statusEq(questionSearchCondition.getStatus()),
                        question.category.id.eq(category.id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Question> countQuery = queryFactory
                .selectFrom(question)
                .leftJoin(question.category, category)
                .leftJoin(question.questions, memberQuestion)
                .leftJoin(memberQuestion.member, member)
                .where(
                        questionTitleEq(questionSearchCondition.getQuestionTitle()),
                        categoryTitleEq(questionSearchCondition.getCategoryTitle()),
                        memberIdEq(questionSearchCondition.getMemberId()),
                        question.category.id.eq(category.id)
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private static NumberExpression<Integer> divisionStatusAboutMemberId(LoginUserDto loginUserDto) {
        return Expressions.cases()
                .when(memberQuestion.member.id.eq(loginUserDto.getMemberId())).then(
                        Expressions.cases()
                                .when(memberQuestion.success.ne(0)).then(1)
                                .when(memberQuestion.fail.ne(0)).then(2)
                                .otherwise(Expressions.constant(0))
                )
                .otherwise(Expressions.constant(0))
                .as("status");
    }


    @Override
    public List<CompetitionQuestionDto> findQuestionWithCompetitionById(Long id) {
        List<CompetitionQuestionDto> content = queryFactory
                .selectFrom(question)
                .leftJoin(question.workbookQuestions, workbookQuestion)
                .leftJoin(workbookQuestion.workbook, workbook)
                .leftJoin(workbook.competition, competition)
                .leftJoin(question.choices, choice)
                .where(competition.id.eq(id))
                .transform(
                        groupBy(question.id).list(
                                Projections.constructor(CompetitionQuestionDto.class,
                                        question.id,
                                        question.description,
                                        list(
                                                Projections.constructor(ChoiceQuestionResponseDto.class,
                                                        choice.number, choice.content
                                                )
                                        ))
                        )
                );
        return content;
    }


    private BooleanExpression categoryTitleEq(String categoryTitle) {
        return StringUtils.hasText(categoryTitle) ? category.categoryTitle.eq(categoryTitle) : null;
    }

    private BooleanExpression questionTitleEq(String questionTitle) {
        return StringUtils.hasText(questionTitle) ? question.title.eq(questionTitle) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private BooleanExpression statusEq(Integer status) {
        if (status != null) {
            if (status.equals(1)) {
                return memberQuestion.success.ne(0);
            } else if (status.equals(2)) {
                return memberQuestion.fail.ne(0);
            }
        }
        return null;
    }


}
