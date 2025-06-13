package com.home.job.company.projections;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface RecruitBoardSelectProjections {
    int getId();
    int getCompanyInfoId();
    String getCompanyName();
    String getCompanyPostcode();
    String getCompanyRoadAddress();
    String getCompanyDetailAddress();
    String getTitle();
    int getHourlyRate();
    String getPeriod();
    String getWorkday();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getBoardText();
    LocalDateTime getCreatedAt();

}
