package com.home.job.main.dto;

import com.home.job.main.entity.ActualWork;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActualWorkDto {
    private int id;

    private int userInfoId;

    private int businessId;

    private LocalDate workDay;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime createdAt;

    // DTO → Entity
    public ActualWork toEntity() {
        return ActualWork.builder()
                .userInfoId(this.userInfoId)
                .businessId(this.businessId)
                .workDay(this.workDay)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .createdAt(this.createdAt)
                .build();
    }

    // Entity -> DTO
    public static ActualWorkDto toDto(ActualWork actualWork) {
        return ActualWorkDto.builder()
                .id(actualWork.getId())
                .userInfoId(actualWork.getUserInfoId())
                .businessId(actualWork.getBusinessId())
                .workDay(actualWork.getWorkDay())
                .startTime(actualWork.getStartTime())
                .endTime(actualWork.getEndTime())
                .createdAt(actualWork.getCreatedAt())
                .build();
    }
}
