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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificaion_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "end_date"),
            @JoinColumn(name = "group_id"),
            @JoinColumn(name = "start_date"),
            @JoinColumn(name = "user_id"),
    })
    private Goal goal;

    @Column
    private LocalDate today_date;

    @Column
    private Boolean daily_check;

    @Builder
    public Certification(Goal goal, LocalDate today_date, Boolean daily_check) {
        this.goal = goal;
        this.today_date = today_date;
        this.daily_check = daily_check;
    }

    public void updateDailyCertificate(Boolean daily_check) {
        this.daily_check = daily_check;
    }
}
