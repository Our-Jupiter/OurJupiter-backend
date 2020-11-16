package com.ourjupiter.springboot.web.dto;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String authorEmail;
    private Long fileId;
    private Group group;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String authorEmail, Long fileId, Group group) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorEmail = authorEmail;
        this.fileId = fileId;
        this.group = group;

    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .authorEmail(authorEmail)
                .fileId(fileId)
                .group(group)
                .build();
    }

}
