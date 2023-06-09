package com.CStudy.domain.member.repository;


import com.CStudy.domain.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query(
        "select m from Member m " +
        "join fetch m.requests " +
        "where m.id = :id"
    )
    Member findMemberFetchRequest(@Param("id") Long id);

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.questions")
    List<Member> findAllWithQuestions();

    @Query("SELECT m FROM Member m JOIN FETCH m.roles WHERE m.email = :email")
    Optional<Member> findByEmailWithRoles(@Param("email") String email);

}
