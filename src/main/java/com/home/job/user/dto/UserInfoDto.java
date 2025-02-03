package com.home.job.user.dto;

import com.home.job.user.entity.UserInfo;
import lombok.Data;

@Data
public class UserInfoDto {
    private int id;

    private String accountId;

    private String accountPw;

    private String name;

    private int age;

    private String phone;

    // DTO → Entity 변환 메서드
    public UserInfo toEntity() {
        return UserInfo.builder()
                .accountId(this.accountId)
                .accountPw(this.accountPw)
                .name(this.name)
                .age(this.age)
                .phone(this.phone)
                .build();
    }
}
