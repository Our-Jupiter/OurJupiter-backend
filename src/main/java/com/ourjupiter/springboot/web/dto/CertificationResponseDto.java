package com.ourjupiter.springboot.web.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CertificationResponseDto {
    private String userName;
    private Long fileId;
    private LocalDate todayDate;

    public CertificationResponseDto(String userName, Long fileId, LocalDate todayDate) {
        this.userName = userName;
        this.fileId = fileId;
        this.todayDate = todayDate;
    }
}
