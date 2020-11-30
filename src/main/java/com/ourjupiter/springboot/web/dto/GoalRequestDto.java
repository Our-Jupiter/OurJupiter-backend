package com.ourjupiter.springboot.web.dto;

import com.ourjupiter.springboot.domain.goal.Goal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalRequestDto {
    private String goal;
    private String penalty;

    @Builder
    public GoalRequestDto(String goal, String penalty) {
        this.goal = goal;
        this.penalty = penalty;
    }

    public Goal updateGoal(Goal findGoal, String goal, String penalty) {
        findGoal.setGoal(goal);
        findGoal.setPenalty(penalty);
        return findGoal;
    }
}
