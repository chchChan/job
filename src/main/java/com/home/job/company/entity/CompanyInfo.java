package com.home.job.company.entity;

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
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(length = 50)
    private String accountId;

    @Column(length = 100)
    private String accountPw;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String contact;

    @Column(length = 50)
    private String postcode;

    @Column(length = 50)
    private String roadAddress;

    @Column(length = 50)
    private String detailAddress;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
