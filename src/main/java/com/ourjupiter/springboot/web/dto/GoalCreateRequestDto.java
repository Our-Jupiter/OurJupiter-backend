package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalCreateRequestDto {
    private Long groupId;
    private String goal;
    private String penalty;

    @Builder
    public GoalCreateRequestDto(Long groupId, String goal, String penalty) {
        this.groupId = groupId;
        this.goal = goal;
        this.penalty = penalty;
    }
}
