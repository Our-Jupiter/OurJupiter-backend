package com.ourjupiter.springboot.domain.group;

import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.posts.Posts;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`Group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Certification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Posts> posts = new ArrayList<Posts>();

    @Builder
    public Group(String name, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public void update(String name) {
        this.name = name;
    }
}
