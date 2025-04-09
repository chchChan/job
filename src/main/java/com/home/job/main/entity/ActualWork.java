package com.home.job.main.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ActualWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int userInfoId;

//    @ManyToOne
//    @JoinColumn(name = "business_id")
//    private Business business;

    @Column
    private int businessId;

//    Date, Time 은 레거시 타입이라 가독성도 안 좋고 시간대(TimeZone) 문제도 쉽게 생김
    @Column
    private LocalDate workDay;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
