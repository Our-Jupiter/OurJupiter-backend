package com.ourjupiter.springboot.web.dto;

import com.ourjupiter.springboot.domain.posts.FileUse;
import lombok.*;
import com.ourjupiter.springboot.domain.posts.Files;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
    private FileUse fileUse;

    public Files toEntity() {
        Files build = Files.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .useWhere(fileUse)
                .build();
        return build;
    }

    @Builder
    public FileDto(Long id, String origFilename, String filename, String filePath, FileUse fileUse) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.fileUse = fileUse;
    }
}

