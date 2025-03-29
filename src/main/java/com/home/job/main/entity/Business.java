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
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int userInfoId;

    @Column(length = 100)
    private String businessName;

    @Column
    private int hourlyRate;

    @Column(length = 50)
    private String payday;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
