package com.home.job.main.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int userInfoId;

    @Column
    private int recruitBoardId;

//    초기값 지정
    @Column(length = 1, nullable = false)
    private String isActive = "Y";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
