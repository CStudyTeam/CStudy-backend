package com.CStudy.domain.comment.repository;

import com.CStudy.domain.comment.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
