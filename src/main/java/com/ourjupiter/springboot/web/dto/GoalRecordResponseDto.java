package com.ourjupiter.springboot.web.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GoalRecordResponseDto {
    private LocalDate startDate;
    private String userName;
    private String penalty;
    private Boolean success;

    public GoalRecordResponseDto(LocalDate startDate, String userName, String penalty, Boolean success) {
        this.startDate = startDate;
        this.userName = userName;
        this.penalty = penalty;
        this.success = success;
    }
}
