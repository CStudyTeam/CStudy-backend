package com.CStudy.domain.question.repository;

import com.CStudy.domain.question.dto.request.QuestionSearchCondition;
import com.CStudy.domain.question.dto.response.QQuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.question.entity.Question;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.CStudy.domain.question.entity.QCategory.category;
import static com.CStudy.domain.question.entity.QMemberQuestion.memberQuestion;
import static com.CStudy.domain.question.entity.QQuestion.question;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<QuestionPageWithCategoryAndTitle> findQuestionPageWithCategory(Pageable pageable, QuestionSearchCondition questionSearchCondition) {

        List<QuestionPageWithCategoryAndTitle> content = queryFactory.select(
                        new QQuestionPageWithCategoryAndTitle(
                                question.id.as("questionId"),
                                question.title.as("questionTitle"),
                                category.categoryTitle.as("categoryTitle"),
                                Expressions.cases()
                                        .when(memberQuestion.success.ne(0)).then(memberQuestion.success)
                                        .otherwise(0)
                                        .as("success")
                        )).from(question)
                .leftJoin(question.category, category)
                .leftJoin(question.questions, memberQuestion)
                .where(
                        questionTitleEq(questionSearchCondition.getQuestionTitle()),
                        categoryTitleEq(questionSearchCondition.getCategoryTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Question> countQuery = queryFactory
                .selectFrom(question)
                .leftJoin(question.category, category)
                .leftJoin(question.questions, memberQuestion)
                .where(
                        questionTitleEq(questionSearchCondition.getQuestionTitle()),
                        categoryTitleEq(questionSearchCondition.getCategoryTitle())
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }


    private BooleanExpression categoryTitleEq(String categoryTitle) {
        return StringUtils.hasText(categoryTitle) ? category.categoryTitle.eq(categoryTitle) : null;
    }

    private BooleanExpression questionTitleEq(String questionTitle) {
        return StringUtils.hasText(questionTitle) ? question.title.eq(questionTitle) : null;
    }
}
