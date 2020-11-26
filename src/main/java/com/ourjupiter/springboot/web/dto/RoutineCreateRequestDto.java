package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RoutineCreateRequestDto {
    private Long groupId;
    private LocalDate startDate;

    @Builder
    public RoutineCreateRequestDto(Long groupId, LocalDate startDate) {
        this.groupId = groupId;
        this.startDate = startDate;
    }
}
