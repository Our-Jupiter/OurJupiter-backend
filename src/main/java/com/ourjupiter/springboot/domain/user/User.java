package com.ourjupiter.springboot.domain.user;

import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 500, nullable = false, unique = true)
    private String email;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 100)
    private String password;

    private String token;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserGroup> groups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Goal> goals = new ArrayList<>();

    @Builder
    public User(String email, String name, String password, String token) {
        this.email = email;
        this.name = name;
        this.password = BCrypt.hashpw(password,BCrypt.gensalt());
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
