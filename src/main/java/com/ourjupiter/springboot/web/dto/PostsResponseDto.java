package com.ourjupiter.springboot.web.dto;

import com.ourjupiter.springboot.domain.posts.Posts;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String authorEmail;
    private LocalDateTime modifiedDate;
    private Long fileId;


    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.authorEmail = entity.getAuthorEmail();
        this.modifiedDate = entity.getModifiedDate();
        this.fileId = entity.getFileId();
    }
}
