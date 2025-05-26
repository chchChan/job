package com.home.job.company.dto;

import com.home.job.company.entity.CompanyInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyInfoDto {
    private int id;

    private String accountId;

    private String accountPw;

    private String name;

    private String contact;

    private String postcode;

    private String roadAddress;

    private String detailAddress;

    private LocalDateTime createdAt;

    // DTO → Entity 변환 메서드
    public CompanyInfo toEntity() {
        return CompanyInfo.builder()
                .accountId(this.accountId)
                .accountPw(this.accountPw)
                .name(this.name)
                .contact(this.contact)
                .postcode(this.postcode)
                .roadAddress(this.roadAddress)
                .detailAddress(this.detailAddress)
                .createdAt(this.createdAt)
                .build();
    }

    // 엔티티 -> DTO 변환 메서드
    public static CompanyInfoDto toDto(CompanyInfo companyInfoDto) {
        return CompanyInfoDto.builder()
                .id(companyInfoDto.getId())
                .accountId(companyInfoDto.getAccountId())
                .accountPw(companyInfoDto.getAccountPw())
                .name(companyInfoDto.getName())
                .contact(companyInfoDto.getContact())
                .postcode(companyInfoDto.getPostcode())
                .roadAddress(companyInfoDto.getRoadAddress())
                .detailAddress(companyInfoDto.getDetailAddress())
                .createdAt(companyInfoDto.getCreatedAt())
                .build();
    }
}
