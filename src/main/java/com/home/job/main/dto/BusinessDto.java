package com.home.job.main.dto;

import com.home.job.main.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessDto {
    private int id;

    private int userInfoId;

    private String businessName;

    private int hourlyRate;

    private String payday;

    private LocalDateTime createdAt;

    // DTO → Entity
    public Business toEntity() {
        return Business.builder()
                .userInfoId(this.userInfoId)
                .businessName(this.businessName)
                .hourlyRate(this.hourlyRate)
                .payday(this.payday)
                .createdAt(this.createdAt)
                .build();
    }

    // Entity -> DTO
    public static BusinessDto toDto(Business business) {
        return BusinessDto.builder()
                .id(business.getId())
                .userInfoId(business.getUserInfoId())
                .businessName(business.getBusinessName())
                .hourlyRate(business.getHourlyRate())
                .payday(business.getPayday())
                .createdAt(business.getCreatedAt())
                .build();
    }
}
