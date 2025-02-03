package com.home.job.user.repository;

import com.home.job.user.entity.FindPwQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindPwQuestionRepository extends JpaRepository<FindPwQuestion, Integer> {
// JPA에서는 기본 키(Primary Key) 타입으로 int 대신 Integer를 사용
}