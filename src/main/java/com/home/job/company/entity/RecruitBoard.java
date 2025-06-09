package com.home.job.company.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecruitBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int companyInfoId;

    @Column(length = 50)
    private String title;

    @Column
    private int hourlyRate;

    @Column(length = 20)
    private String period;

    @Column(length = 20)
    private String workday;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    private String boardText;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
