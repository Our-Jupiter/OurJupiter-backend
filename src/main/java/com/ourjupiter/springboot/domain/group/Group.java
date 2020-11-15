package com.ourjupiter.springboot.domain.group;

import com.ourjupiter.springboot.domain.posts.Posts;
import com.ourjupiter.springboot.domain.user.User;
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

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "group")
    private List<User> user = new ArrayList<User>();

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
