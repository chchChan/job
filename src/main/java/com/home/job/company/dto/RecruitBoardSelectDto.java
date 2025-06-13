package com.home.job.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class RecruitBoardSelectDto {
    private int id;
    private int companyInfoId;
    private String companyName;
    private String companyPostcode;
    private String companyRoadAddress;
    private String companyDetailAddress;
    private String title;
    private int hourlyRate;
    private String period;
    private String workday;
    private LocalTime startTime;
    private LocalTime endTime;
    private String boardText;
    private LocalDateTime createdAt;

}
