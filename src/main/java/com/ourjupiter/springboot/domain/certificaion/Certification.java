package com.ourjupiter.springboot.domain.certificaion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourjupiter.springboot.domain.goal.GoalPK;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.goal.Goal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificaion_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
            @JoinColumn(name = "end_date",updatable = false),
            @JoinColumn(name = "group_id",updatable = false),
            @JoinColumn(name = "start_date",updatable = false),
            @JoinColumn(name = "user_id",updatable = false),
    })
    private Goal goal;

    @Column(updatable = false)
    private LocalDate todayDate;

    @Column
    private Boolean dailyCheck;

    @Column
    private Long fileId;

    @Builder
    public Certification(Goal goal, LocalDate today_date, Boolean daily_check, Long fileId) {
        this.goal = goal;
        this.todayDate = today_date;
        this.dailyCheck = daily_check;
        this.fileId = fileId;
    }

    public void updateFile(Long fileId) {
        this.fileId = fileId;
    }
    public void dailyCheck(Boolean dailyCheck) {
        this.dailyCheck = dailyCheck;
    }
}
