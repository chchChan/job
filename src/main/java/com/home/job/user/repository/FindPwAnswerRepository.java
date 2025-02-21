package com.home.job.user.repository;

import com.home.job.user.entity.FindPwAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FindPwAnswerRepository extends JpaRepository<FindPwAnswer, Integer> {
    Optional<FindPwAnswer> findByUserInfoId(int userInfoId);
}
