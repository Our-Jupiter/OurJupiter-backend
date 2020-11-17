package com.ourjupiter.springboot.domain.user_group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserGroup {
    @EmbeddedId
    private UserGroupPK id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    private Group group;

    @Column(name = "joined")
    @ColumnDefault("0") //default 0
    private Integer joined;

    @Builder
    public UserGroup(User user, Group group, Integer joined) {
        this.user = user;
        this.group = group;
        this.joined = joined;
        this.id = new UserGroupPK(user.getId(), group.getId());
    }

    public void update(Integer joined) {
        this.joined = joined;
    }
}
