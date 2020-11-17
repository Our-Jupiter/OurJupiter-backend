package com.ourjupiter.springboot.domain.user_group;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserGroupPK implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    private UserGroupPK() {}

    public UserGroupPK(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
