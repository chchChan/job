package com.home.job.user.service;

import com.home.job.user.dto.FindPwQuestion;
import com.home.job.user.repository.FindPwQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private FindPwQuestionRepository repository;

    public List<FindPwQuestion> getAllQuestions() {
        return repository.findAll(); // findAll : 모든 데이터를 조회
    }
}
