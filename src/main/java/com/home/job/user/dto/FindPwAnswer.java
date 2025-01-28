package com.home.job.user.dto;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column
    private LocalDateTime createdAt;
}
