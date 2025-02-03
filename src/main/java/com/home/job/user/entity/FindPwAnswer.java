package com.home.job.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class FindPwAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int findPwQuestionId;

    @Column
    private int userInfoId;

    @Column(length = 500)
    private String answer;

    @CreationTimestamp  // 자동으로 현재 시간 설정
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
