package com.ourjupiter.springboot.domain.goal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.user.User;
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
public class Goal {
    @EmbeddedId
    private GoalPK id;

    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    private User user;

    @JsonIgnore
    @ManyToOne
    @MapsId("groupId")
    private Group group;

    @Column
    private String goal;

    @Column
    private String penalty;

    @Column
    private Boolean success;

    @Column
    private Boolean penalty_certificate;

    @Column
    private Integer penalty_approved_num;

    @Column
    private Boolean is_expired;

    @Builder
    public Goal(LocalDate start_date, LocalDate end_date, User user, Group group, String goal, String penalty,
                Boolean success, Boolean penalty_certificate, Integer penalty_approved_num, Boolean is_expired) {
        this.user = user;
        this.group = group;
        this.goal = goal;
        this.penalty = penalty;
        this.success = success;
        this.penalty_certificate = penalty_certificate;
        this.penalty_approved_num = penalty_approved_num;
        this.is_expired = is_expired;
        this.id = new GoalPK(start_date, end_date, user.getId(), group.getId());
    }

    public void updateGoal(String goal) { this.goal = goal; }

    public void updatePenalty(String penalty) { this.penalty = penalty; }

    public void updateSuccess(Boolean success) {
        this.success = success;
    }

    public void updateCertificate(Boolean penalty_certificate) {
        this.penalty_certificate = penalty_certificate;
    }

    public void updateApprovedNum() {
        this.penalty_approved_num += 1;
    }
}
