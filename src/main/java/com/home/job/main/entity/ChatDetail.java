package com.home.job.main.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int chatRoomId;

    @Column
    private String content;

    @Column
    private int fromId;

    @Column(length = 1)
    private String fromInfo;

    @Column(length = 1)
    private String isReading;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
