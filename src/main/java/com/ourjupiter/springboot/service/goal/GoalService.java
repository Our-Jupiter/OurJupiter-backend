package com.ourjupiter.springboot.service.goal;


import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.certificaion.CertificationPK;
import com.ourjupiter.springboot.domain.certificaion.CertificationRepository;
import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.goal.GoalPK;
import com.ourjupiter.springboot.domain.goal.Goal;
import com.ourjupiter.springboot.domain.goal.GoalRepository;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
import com.ourjupiter.springboot.web.dto.GoalRequestDto;
import com.ourjupiter.springboot.web.dto.RoutineCreateRequestDto;
import com.ourjupiter.springboot.web.dto.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GoalService {
    private final GoalRepository goalRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final CertificationRepository certificationRepository;


    @Transactional
    public String createRoutine(String token, RoutineCreateRequestDto routineCreateRequestDto){
        User user = userRepository.findByToken(token).get();
        Group group = groupRepository.findById(routineCreateRequestDto.getGroupId()).get();

        if (user.getId() != group.getOwnerId()) {
            throw new UnauthorizedException("권한이 없습니다 .");
        }

        LocalDate startDate = routineCreateRequestDto.getStartDate();
        LocalDate endDate = startDate.plusDays(14);

        List<UserGroup> members = userGroupRepository.findByGroupId(group.getId());
        members.forEach(m -> goalRepository.save(
                Goal.builder()
                        .start_date(startDate.plusDays(1))
                        .end_date(endDate)
                        .user(userRepository.findById(m.getUser().getId()).get())
                        .group(group)
                        .goal("")
                        .penalty("")
                        .success(false)
                        .penalty_certificate(false)
                        .penalty_approved_num(0)
                        .is_expired(false)
                        .build()
        ));

        Goal findGoal = goalRepository.findActiveRoutineByIds(user.getId(), group.getId());

        certificationRepository.save(
                Certification.builder()
                        .today_date(startDate.plusDays(1))
                        .daily_check(false)
                        .goal(findGoal)
                        .build()
        );
        System.out.println("***"+findGoal);

            /*members.forEach(m -> certificationRepository.save(
                    Certification.builder()
                            .today_date(startDate)
                            .daily_check(false)
                            .goal(findGoal)
                            .build()
            ));*/
        return "루틴 생성 성공";
    }

    @Transactional
    public LocalDate hasRoutine(Long groupId){
        List<Goal> routines = goalRepository.findActiveRoutine(groupId);
        if (routines.size() == 0) return null;
        return routines.get(0).getId().getStartDate();
    }

    @Transactional
    public Map<String, String> getGoalPenalty(String token, Long groupId){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        Goal findGoal = goalRepository.findActiveRoutineByIds(user.getId(), group.getId());

        HashMap<String, String> map = new HashMap<>();
        map.put("goal", findGoal.getGoal());
        map.put("penalty", findGoal.getPenalty());

        return map;
    }

    @Transactional
    public String setGoalPenalty(String token, GoalRequestDto goalRequestDto, Long groupId){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        Goal findGoal = goalRepository.findActiveRoutineByIds(user.getId(), group.getId());

        findGoal.updateGoal(goalRequestDto.getGoal());
        findGoal.updatePenalty(goalRequestDto.getPenalty());

        return "목표 패널티 설정 성공";
    }

    @Transactional
    public String deleteGoal(Long groupId, String token){
        User user = userRepository.findByToken(token).get();
        Group group = groupRepository.findById(groupId).get();

        if (user.getId() != group.getOwnerId()) {
            throw new UnauthorizedException("권한이 없습니다 .");
        }
        //System.out.println("********");
        List<Goal> goals = goalRepository.findByGroupId(groupId);
        //System.out.println("****"+goals.get(0).getGroup().getId());
        goals.forEach(g ->goalRepository.delete(g));

        return "목표 삭제 성공";
    }
}
