package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.posts.PostsService;
import com.ourjupiter.springboot.service.posts.FileService;
import com.ourjupiter.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final FileService fileService;

    @CrossOrigin("*")
    @PostMapping("/board")
    public String save(@RequestParam("file") MultipartFile files, PostsSaveRequestDto requestDto) {
        try {
            String origFilename = files.getOriginalFilename();
            if (origFilename.length()!=0) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + origFilename;
                String savePath = System.getProperty("user.dir") + "/src/main/resources/files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;

                files.transferTo(new File(filePath));

                FileDto fileDto = new FileDto();
                fileDto.setOrigFilename(origFilename);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);

                Long fileId = fileService.saveFile(fileDto);
                requestDto.setFileId(fileId);
            }

            postsService.save(requestDto);


} catch (Exception e) {
            e.printStackTrace();
        }
        return "board/index";
    }

    @CrossOrigin("*")
    @PutMapping("/board/{id}")
    public String update(@PathVariable Long id, @RequestParam("file") MultipartFile files,PostsUpdateRequestDto requestDto) throws IOException {
        PostsResponseDto oldResponse = postsService.findById(id);
        Long oldFileId = oldResponse.getFileId();
        FileDto oldFileDto = fileService.getFile(oldFileId);//원래 있던 파일 정보
        String origFilename = files.getOriginalFilename();//새로운 파일 이름

        if (!origFilename.equals("")) {
            File deleteFile = new File(oldFileDto.getFilePath());
            if(deleteFile.exists()) {
                // 파일을 삭제합니다.
                deleteFile.delete();
                fileService.delete(oldFileId);
                System.out.println("파일을 삭제하였습니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
            }

            UUID uuid = UUID.randomUUID();
            String filename = uuid + origFilename;
            String savePath = System.getProperty("user.dir") + "/src/main/resources/files";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;

            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            requestDto.setFileId(fileId);
        }
        else {
            requestDto.setFileId(oldFileId);
        }
        postsService.update(id,requestDto);

        return "board/index";
    }

    @CrossOrigin("*")
    @DeleteMapping("/board/{id}")
    public String delete(@PathVariable Long id) {

        PostsResponseDto Response = postsService.findById(id);
        Long fileId = Response.getFileId();
        FileDto FileDto = fileService.getFile(fileId);
        File deleteFile = new File(FileDto.getFilePath());
        if(deleteFile.exists()) {
            deleteFile.delete();
            fileService.delete(fileId);
            System.out.println("파일을 삭제하였습니다.");
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }

        postsService.delete(id);
        return "";
    }

    @CrossOrigin("*")
    @GetMapping("/board")
    public List<PostsListResponseDto> findAllDesc() {
        return postsService.findAllDesc();
    }

    @CrossOrigin("*")
    @GetMapping("/board/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);

        return postsService.findById(id);
    }

    @CrossOrigin("*")
    @GetMapping("/board/file/{id}")
    public ResponseEntity<InputStreamResource> fileStream(@PathVariable Long id) throws IOException {
        FileDto fileDto = fileService.getFile(id);
        Path path = Paths.get(fileDto.getFilePath());

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }
}