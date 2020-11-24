package com.ourjupiter.springboot.service.posts;

import com.ourjupiter.springboot.domain.posts.Files;
import com.ourjupiter.springboot.domain.posts.FilesRepository;
import com.ourjupiter.springboot.domain.posts.Posts;
import com.ourjupiter.springboot.web.dto.FileDto;
import com.ourjupiter.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    private FilesRepository filesRepository;

    public FileService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return filesRepository.save(fileDto.toEntity()).getId();
    }

    public void delete (Long id) {
        Files files = filesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일 없습니다. id=" + id));

        filesRepository.delete(files);
    }

    @Transactional
    public FileDto getFile(Long id) {
        Files file = filesRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .fileUse(file.getUseWhere())
                .build();
        return fileDto;
    }
}
