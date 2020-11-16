package com.ourjupiter.springboot.domain.posts;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.web.dto.PostsUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ourjupiter.springboot.domain.BaseTimeEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private String authorEmail;

    @Column
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public Posts(String title, String content, String author, String authorEmail, Long fileId, Group group) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
        this.fileId = fileId;
        this.group = group;
    }
    public void update(String title, String content, Long fileId) {
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }
}
