package com.ourjupiter.springboot.domain.certificaion;

import lombok.Getter;
import org.springframework.jndi.JndiLocatorDelegate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
public class CertificationPK implements Serializable{

    @Column(name = "today_date")
    private LocalDate todayDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    private CertificationPK() {}

    public CertificationPK(LocalDate todayDate, LocalDate startDate, Long userId, Long groupId) {
        this.todayDate = todayDate;
        this.startDate = startDate;
        this.userId = userId;
        this.groupId = groupId;
    }
}
