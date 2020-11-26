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
import com.ourjupiter.springboot.web.dto.RoutineCreateRequestDto;
import com.ourjupiter.springboot.web.dto.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public String createCertificationRow(String token, CertificationCreateRequestDto certificationCreateRequestDto){
        User user = userRepository.findByToken(token).get();
        Group group = groupRepository.findById(certificationCreateRequestDto.getGroupId()).get();

        if (user.getId() != group.getOwnerId()) {
            throw new UnauthorizedException("권한이 없습니다 .");
        }

        LocalDate todayDate = certificationCreateRequestDto.getStartDate();
        //LocalDate endDate = startDate.plusDays(14);

        Goal findGoal = goalRepository.findActiveRoutineByIds(user.getId(), group.getId());

        List<UserGroup> members = userGroupRepository.findByGroupId(group.getId());

        System.out.println("***"+findGoal.getId());

        certificationRepository.save(
                Certification.builder()
                        .today_date(todayDate)
                        .daily_check(false)
                        .goal(findGoal)
                        .build()
        );
        /*for (int i = 0; i < 14; i++){
            members.forEach(m -> certificationRepository.save(
                    Certification.builder()
                            .today_date(todayDate)
                            .goal(findGoal)
                            .daily_check(false)
                            .build()
            ));
            todayDate.plusDays(1);
        }*/

        return "루틴 생성 성공";
    }

}
