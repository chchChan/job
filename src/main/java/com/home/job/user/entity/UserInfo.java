package com.home.job.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
//@Table(name = "user_Info")  // DB의 테이블이름과 동일하다면 @Table을 쓰지 않아도 자동매칭
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자 문제 해결
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id // 해당 필드를 Primary Key로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT 기능을 사용하여 ID 값을 자동 증가
    // 원래 Table 생성 -> DTO 생성으로 했다면 JPA는 Table 생성 안하고 Entity 선언 시 Table 생성(Hibernate)
    private Integer id;

    @Column(length = 50)  // VARCHAR(50)
    private String accountId;

    @Column(length = 100)
    private String accountPw;

    @Column(length = 20)
    private String name;

    @Column
    private int age;

    @Column(length = 30)
    private String phone;

    @CreationTimestamp  // 자동으로 현재 시간 설정
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
