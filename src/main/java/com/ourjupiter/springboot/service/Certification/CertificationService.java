package com.ourjupiter.springboot.service.Certification;

import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.certificaion.CertificationRepository;
import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.goal.GoalRepository;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
import com.ourjupiter.springboot.web.dto.CertificationCreateRequestDto;
import com.ourjupiter.springboot.web.dto.PostsListResponseDto;
import com.ourjupiter.springboot.web.dto.UnauthorizedException;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CertificationService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GoalRepository goalRepository;
    private final CertificationRepository certificationRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public String createDailyCertification(CertificationCreateRequestDto certificationCreateRequestDto) {

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
                element.dailyCheck(true);
                element.updateFile(certificationCreateRequestDto.getFileId());
            }
        }

        return "오늘 인증 성공";
    }

    @Transactional
    public List<Pair<String, Long>> getDailyCertification(String token, Long groupId) {

        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        List<Object[]> findCertification = certificationRepository.findByDaily(groupId);
        List<Pair<String, Long>> goalList = new ArrayList<>();

        for (Object[] element : findCertification) {
            String date = element[2].toString();
            if (date.equals(LocalDate.now().format(formatter))){
                if (element[1] == null){
                    goalList.add(new Pair<>(element[0].toString(),null));
                }
                else {
                    goalList.add(new Pair<>(element[0].toString(),Long.parseLong(element[1].toString())));
                }
            }
        }
        return goalList;
    }

    @Transactional
    public Boolean getDailyCheck(String token, Long groupId){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        List<Certification> findCertification = certificationRepository.findByGoal_Group_IdAndGoal_Id_UserId(group.getId(), user.getId());

        Boolean check = false;

        for (Certification element : findCertification) {
            if (element.getTodayDate().format(formatter).equals(LocalDate.now().format(formatter))) {
                check = element.getDailyCheck();
            }
        }
        return check;
    }

}
