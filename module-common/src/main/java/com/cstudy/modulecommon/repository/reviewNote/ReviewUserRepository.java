package com.cstudy.modulecommon.repository.reviewNote;

import com.cstudy.modulecommon.domain.reviewQuestion.ReviewUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewUserRepository extends MongoRepository<ReviewUser, String> {
    Optional<ReviewUser> findByUserName(String userName);
}