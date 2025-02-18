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

//     연관관계 필드 - insertable, updatable를 false로 설정하여 DB 컬럼과 중복되지 않도록 함
//    @OneToOne
//    @JoinColumn(name = "user_info_id", insertable = false, updatable = false)
//    private UserInfo userInfo;

    @Column(length = 500)
    private String answer;

    @CreationTimestamp  // 자동으로 현재 시간 설정
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
