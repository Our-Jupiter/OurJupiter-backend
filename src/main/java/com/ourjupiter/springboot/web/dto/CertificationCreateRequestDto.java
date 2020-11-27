package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CertificationCreateRequestDto {
    private String groupId;
    private String userId;
    private String todayDate;
    private Long fileId;

    @Builder
    public CertificationCreateRequestDto(String groupId, String userId, String todayDate, Long fileId) {
        this.groupId = groupId;
        this.todayDate = todayDate;
        this.userId = userId;
        this.fileId = fileId;
    }
}
