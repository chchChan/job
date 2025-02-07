package com.home.job.user.dto;

import com.home.job.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor  // Lombok 어노테이션을 사용하여 모든 필드를 받는 생성자 생성
@NoArgsConstructor   // 기본 생성자
@Builder
public class UserInfoDto {
    private int id;

    private String accountId;

    private String accountPw;

    private String name;

    private int age;

    private String phone;

    private LocalDateTime created_at;

    // DTO → Entity 변환 메서드
    public UserInfo toEntity() {
        return UserInfo.builder()
                .accountId(this.accountId)
                .accountPw(this.accountPw)
                .name(this.name)
                .age(this.age)
                .phone(this.phone)
                .createdAt(this.created_at)
                .build();
    }

    // 엔티티 -> DTO 변환 메서드
    public static UserInfoDto fromEntity(UserInfo userInfo) {
        return UserInfoDto.builder()
                .accountId(userInfo.getAccountId())
                .accountPw(userInfo.getAccountPw())
                .name(userInfo.getName())
                .age(userInfo.getAge())
                .phone(userInfo.getPhone())
                .created_at(userInfo.getCreatedAt())
                .build();
    }
}
