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
//@IdClass(GoalPK.class)
public class Goal {
    @EmbeddedId
    private GoalPK id;

//    @JsonIgnore
//    @MapsId("startDate")
//    private String start_date;

    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Group group;

    @Column(length = 100)
    private String goal;

    @Column(length = 100)
    private String penalty;

    @Column
    private Boolean success;

    @Column
    private Boolean penalty_certificate;

    @Column
    private Integer penalty_approved_num;

    @Builder
    public Goal(LocalDate start_date, User user, Group group, String goal, String penalty,
                Boolean success, Boolean penalty_certificate, Integer penalty_approved_num) {
        //this.start_date = start_date;
        this.user = user;
        this.group = group;
        this.goal = goal;
        this.penalty = penalty;
        this.success = success;
        this.penalty_certificate = penalty_certificate;
        this.penalty_approved_num = penalty_approved_num;
        this.id = new GoalPK(start_date, user.getId(), group.getId());
    }

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
