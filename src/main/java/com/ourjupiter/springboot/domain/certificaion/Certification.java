package com.ourjupiter.springboot.domain.certificaion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourjupiter.springboot.domain.goal.GoalPK;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.goal.Goal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Certification {
    @EmbeddedId
    private CertificationPK id;

    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    private User user;

    @JsonIgnore
    @ManyToOne
    @MapsId("groupId")
    private Group group;

    @JsonIgnore
    @ManyToOne
    @MapsId("startDate")
    private Goal goal;

    @Column
    private Boolean daily_check;

    @Builder
    public Certification(LocalDate today_date, User user, Group group, Goal goal, Boolean daily_check) {
        this.user = user;
        this.group = group;
        this.goal = goal;
        this.daily_check = daily_check;
        this.id = new CertificationPK(today_date, goal.getId().getStartDate(), user.getId(), group.getId());
    }

    public void updateDailyCertificate(Boolean daily_check) {
        this.daily_check = daily_check;
    }
}
