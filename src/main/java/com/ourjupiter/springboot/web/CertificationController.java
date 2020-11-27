package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.domain.posts.FileUse;
import com.ourjupiter.springboot.service.Certification.CertificationService;
import com.ourjupiter.springboot.service.goal.GoalService;
import com.ourjupiter.springboot.service.posts.FileService;
import com.ourjupiter.springboot.web.dto.CertificationCreateRequestDto;
import com.ourjupiter.springboot.web.dto.FileDto;
import com.ourjupiter.springboot.web.dto.PostsSaveRequestDto;
import com.ourjupiter.springboot.web.dto.RoutineCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CertificationController {
    private final FileService fileService;
    private final CertificationService certificationService;

    @CrossOrigin("*")
    @PostMapping("/certification")
    public String createCertification(@RequestParam("file") MultipartFile files, CertificationCreateRequestDto certificationCreateRequestDto) {
        try {
            String origFilename = files.getOriginalFilename();
            if (origFilename.length() != 0) {
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
                fileDto.setFileUse(FileUse.CERTIFICATION);

                Long fileId = fileService.saveFile(fileDto);
                certificationCreateRequestDto.setFileId(fileId);
            }
            certificationService.createDailyCertification(certificationCreateRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
