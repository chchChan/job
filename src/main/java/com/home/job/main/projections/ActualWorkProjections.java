package com.home.job.main.projections;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ActualWorkProjections {
    int getId();
    int getUserInfoId();
    int getBusinessId();
    LocalDate getWorkDay();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getBusinessName();
    int getHourlyRate();
}
