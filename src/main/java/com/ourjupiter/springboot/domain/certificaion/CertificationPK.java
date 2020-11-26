package com.ourjupiter.springboot.domain.certificaion;

import com.ourjupiter.springboot.domain.goal.GoalPK;
import lombok.Getter;
import org.springframework.jndi.JndiLocatorDelegate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
public class CertificationPK implements Serializable{

    @Column(name = "goalPK")
    private GoalPK goalPK;

    @Column(name = "today_date")
    private LocalDate todayDate;

    private CertificationPK() {}

    public CertificationPK(LocalDate todayDate, GoalPK goalPK) {
        this.todayDate = todayDate;
        //System.out.println("***"+goalPK);
        this.goalPK = goalPK;
    }
}
