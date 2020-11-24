package com.ourjupiter.springboot.domain.goal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class GoalPK implements Serializable {
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    private GoalPK() {}

    public GoalPK(LocalDate startDate, LocalDate endDate, Long userId, Long groupId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.groupId = groupId;
    }
}
