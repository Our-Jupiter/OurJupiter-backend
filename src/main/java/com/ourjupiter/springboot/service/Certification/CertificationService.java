package com.ourjupiter.springboot.service.Certification;

import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.certificaion.CertificationRepository;
import com.ourjupiter.springboot.domain.goal.GoalRepository;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
import com.ourjupiter.springboot.web.dto.CertificationCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificationService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GoalRepository goalRepository;
    private final CertificationRepository certificationRepository;

    @Transactional
    public String createDailyCertification(CertificationCreateRequestDto certificationCreateRequestDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Group group = groupRepository.findById(Long.parseLong(certificationCreateRequestDto.getGroupId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + certificationCreateRequestDto.getGroupId()));

        User user = userRepository.findById(Long.parseLong(certificationCreateRequestDto.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + certificationCreateRequestDto.getUserId()));

        List<Certification> findCertification = certificationRepository.findByGoal_Group_IdAndGoal_Id_UserId(
                Long.parseLong(certificationCreateRequestDto.getGroupId()),
                Long.parseLong(certificationCreateRequestDto.getUserId())
        );
        for (Certification element : findCertification) {
            if (element.getTodayDate().format(formatter).equals(certificationCreateRequestDto.getTodayDate())) {
                System.out.println("%%%%"+element.getTodayDate().format(formatter));
                element.dailyCheck(true);
                element.updateFile(certificationCreateRequestDto.getFileId());
            }
        }

        return "오늘 인증 성공";
    }

}
