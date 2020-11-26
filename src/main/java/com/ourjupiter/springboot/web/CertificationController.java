package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.Certification.CertificationService;
import com.ourjupiter.springboot.service.goal.GoalService;
import com.ourjupiter.springboot.web.dto.CertificationCreateRequestDto;
import com.ourjupiter.springboot.web.dto.RoutineCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CertificationController {
    private final CertificationService certificationService;

    @CrossOrigin("*")
    @PostMapping("/certification")
    public String createCertificationRow(@RequestHeader("x-access-token") String token,
                                @RequestBody CertificationCreateRequestDto certificationCreateRequestDto) {

        return certificationService.createCertificationRow(token, certificationCreateRequestDto );
    }
}
