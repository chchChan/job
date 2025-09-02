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
    private int roomId;

    @Column
    private String message;

    @Column
    private int senderId;

    @Column(length = 10)
    private String senderType;

    @Builder.Default
    @Column(length = 1, nullable = false)
    private String isReading = "N";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
