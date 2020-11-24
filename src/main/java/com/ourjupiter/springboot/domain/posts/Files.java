package com.ourjupiter.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ourjupiter.springboot.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Files extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Convert(converter = FileUseConverter.class)
    @Column(nullable = false)
    private FileUse useWhere;

    @Builder
    public Files(Long id, String origFilename, String filename, String filePath, FileUse useWhere) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.useWhere = useWhere;
    }
}
