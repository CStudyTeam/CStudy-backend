package com.CStudy.domain.request.repository;

import com.CStudy.domain.request.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
