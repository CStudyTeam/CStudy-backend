package com.CStudy.domain.request.repository;

import com.CStudy.domain.request.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(
        "select r from Request r " +
        "where r.member.id = :id"
    )
    Page<Request> findRequestByMemberId(@Param("id") Long id,
            Pageable pageable);
}
