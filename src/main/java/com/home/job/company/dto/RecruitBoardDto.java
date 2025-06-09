package com.home.job.company.dto;

import com.home.job.company.entity.RecruitBoard;
import com.home.job.main.dto.ActualWorkDto;
import com.home.job.main.entity.ActualWork;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitBoardDto {
    private int id;

    private int companyInfoId;

    private String title;

    private int hourlyRate;

    private String period;

    private String workday;

    private LocalTime startTime;

    private LocalTime endTime;

    private String boardText;

    private LocalDateTime createdAt;

    // DTO → Entity
    public RecruitBoard toEntity() {
        return RecruitBoard.builder()
                .companyInfoId(this.companyInfoId)
                .title(this.title)
                .hourlyRate(this.hourlyRate)
                .period(this.period)
                .workday(this.workday)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .boardText(this.boardText)
                .createdAt(this.createdAt)
                .build();
    }

    // Entity -> DTO
    public static RecruitBoardDto toDto(RecruitBoard RecruitBoard) {
        return RecruitBoardDto.builder()
                .id(RecruitBoard.getId())
                .companyInfoId(RecruitBoard.getCompanyInfoId())
                .title(RecruitBoard.getTitle())
                .hourlyRate(RecruitBoard.getHourlyRate())
                .period(RecruitBoard.getPeriod())
                .workday(RecruitBoard.getWorkday())
                .startTime(RecruitBoard.getStartTime())
                .endTime(RecruitBoard.getEndTime())
                .boardText(RecruitBoard.getBoardText())
                .createdAt(RecruitBoard.getCreatedAt())
                .build();
    }
}
