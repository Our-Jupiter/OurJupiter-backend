package com.ourjupiter.springboot.domain.goal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Goal {
    @EmbeddedId
    @Column(name = "goalPK")
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
    private Integer successNum;

    @Column
    private Boolean doFeedback;

    @Column
    private Boolean penaltyCertificate;

    @Column
    private Integer penaltyApproved_num;

    @Column
    private Boolean isExpired;

    @OneToMany(
            mappedBy = "goal"
            )
    private List<Certification> certifications = new ArrayList<>();

    @Builder
    public Goal(LocalDate start_date, LocalDate end_date, User user, Group group, String goal, String penalty,
                Boolean success, Integer success_num, Boolean do_feedback,
                Boolean penalty_certificate, Integer penalty_approved_num, Boolean is_expired) {
        this.user = user;
        this.group = group;
        this.goal = goal;
        this.penalty = penalty;
        this.success = success;
        this.successNum = success_num;
        this.doFeedback = do_feedback;
        this.penaltyCertificate = penalty_certificate;
        this.penaltyApproved_num = penalty_approved_num;
        this.isExpired = is_expired;
        this.id = new GoalPK(start_date, end_date, user.getId(), group.getId());
    }

    public void updateGoal(String goal) { this.goal = goal; }

    public void updatePenalty(String penalty) { this.penalty = penalty; }

    public void updateSuccessNum() {
        this.successNum += 1;
    }

    public void updateDoFeedback() {
        this.doFeedback = true;
    }

    public void updateCertificate(Boolean penalty_certificate) {
        this.penaltyCertificate = penalty_certificate;
    }

    public void updateApprovedNum() {
        this.penaltyApproved_num += 1;
    }
}
