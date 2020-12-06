package com.ourjupiter.springboot.service.goal;

import com.ourjupiter.springboot.domain.certificaion.Certification;
import com.ourjupiter.springboot.domain.certificaion.CertificationPK;
import com.ourjupiter.springboot.domain.certificaion.CertificationRepository;
import com.ourjupiter.springboot.domain.goal.Goal;

import com.ourjupiter.springboot.domain.goal.GoalRepository;
import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
import com.ourjupiter.springboot.web.dto.*;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public String createRoutine(String token, RoutineCreateRequestDto routineCreateRequestDto) {

        User user = userRepository.findByToken(token).get();
        Group group = groupRepository.findById(routineCreateRequestDto.getGroupId()).get();

        if (user.getId() != group.getOwnerId()) {
            throw new UnauthorizedException("권한이 없습니다 .");
        }

        LocalDate startDate = routineCreateRequestDto.getStartDate();
        LocalDate todayDate = startDate;
        LocalDate endDate = startDate.plusDays(14);

        List<UserGroup> members = userGroupRepository.findByGroupId(group.getId());
        for (UserGroup element : members) {
            Goal g = goalRepository.save(
                    Goal.builder()
                            .start_date(startDate.plusDays(1))
                            .end_date(endDate)
                            .user(userRepository.findById(element.getUser().getId()).get())
                            .group(group)
                            .goal("")
                            .penalty("")
                            .success(false)
                            .success_num(0)
                            .do_feedback(false)
                            .penalty_certificate(false)
                            .penalty_approved_num(0)
                            .is_expired(false)
                            .build()
            );

            for (int i = 0; i < 14; i++) {
                certificationRepository.save(
                        Certification.builder()
                                .today_date(todayDate.plusDays(1))
                                .daily_check(false)
                                .goal(g)
                                .fileId(null)
                                .build()
                );
                todayDate = todayDate.plusDays(1);
            }
            todayDate = startDate;
        }
        return "루틴 생성 성공";
    }

    @Transactional
    public LocalDate hasRoutine(Long groupId) {
        List<Goal> routines = goalRepository.findActiveRoutine(groupId);
        if (routines.size() == 0) return null;
        return routines.get(0).getId().getStartDate();
    }

    @Transactional
    public Map<String, String> getGoalPenalty(String token, Long groupId) {
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
    public String setGoalPenalty(String token, GoalRequestDto goalRequestDto, Long groupId) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        //Goal findGoal = goalRepository.findActiveRoutineByIds(user.getId(), group.getId());

        goalRepository.updateGoalPenalty(goalRequestDto.getGoal(), goalRequestDto.getPenalty(), user.getId(), group.getId());

        return "목표 패널티 설정 성공";
    }

    @Transactional
    public List<Pair<String, String>> getGoalList(String token, Long groupId){
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        List<Goal> findGoal = goalRepository.findActiveRoutine(groupId);

        List<Pair<String, String>> goalList = new ArrayList<>();
        findGoal.forEach(g -> goalList.add(new Pair<>(g.getUser().getName(), g.getGoal())));

        return goalList;
    }

    @Transactional
    public String deleteGoal(Long groupId, String token) {
        User user = userRepository.findByToken(token).get();
        Group group = groupRepository.findById(groupId).get();

        if (user.getId() != group.getOwnerId()) {
            throw new UnauthorizedException("권한이 없습니다 .");
        }

        List<Goal> goals = goalRepository.findByGroupId(groupId);
        int count = goals.size();
        //goalRepository.delete(goalRepository.findByIdGroupId(groupId).get());
        /*for (int i = 0; i < count; i++) {
            goalRepository.deleteInBatch(goals);
        }*/
        //goals.forEach(g -> goalRepository.delete(g));

        return "목표 삭제 성공";
    }

    @Transactional
    public String setFeedback(String token, List<String> feedback, Long groupId) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("권한이 없습니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        //Goal goal = goalRepository.findActiveRoutineByIds(user.getId(), groupId);
        goalRepository.updateDoFeedback(true, user.getId(), groupId);

        feedback.forEach(f -> {
            if (!f.equals("")) {
                User u = userRepository.findByName(f).get();
                //Goal findGoal = goalRepository.findActiveRoutineByIds(u.getId(), group.getId());
                goalRepository.updateSuccessNum(u.getId(), groupId);
            }
        });
        return "피드백 반영 성공";
    }

    @Transactional
    public Boolean getDoFeedback(String token, Long groupId) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("권한이 없습니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        Goal goal = goalRepository.findActiveRoutineByIds(user.getId(), groupId);

        return goal.getDoFeedback();
    }

    @Transactional
    public List<Pair<String, List<GoalRecordResponseDto>>> getRecord(String token, Long groupId) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("권한이 없습니다 ."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UnauthorizedException("없는 그룹입니다 ."));

        List<Goal> findRecord = goalRepository.findGoalRecords(groupId);
        List<Pair<String, List<GoalRecordResponseDto>>> result = new ArrayList<>();
        List<GoalRecordResponseDto> RecordList = new ArrayList<>();

        String startDate= "";
        int index = 0;
        for (Goal element : findRecord) {
            index++;
            if (!element.getId().getStartDate().format(formatter).equals(startDate)) {
                if (!startDate.equals("")) {
                    result.add(new Pair<>(startDate, RecordList));
                    RecordList = new ArrayList<>();
                }
                startDate = element.getId().getStartDate().format(formatter);
            }
            RecordList.add(new GoalRecordResponseDto(element.getId().getStartDate(), element.getUser().getName(), element.getPenalty(), element.getSuccess()));
        }

        if (findRecord.size() != 0) {
            result.add(new Pair<>(findRecord.get(index - 1).getId().getStartDate().format(formatter), RecordList));
        }

        return result;
    }

    @Transactional
    public Boolean endRoutine(String token, Long id){

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        List<UserGroup> groupMembers = userGroupRepository.findByGroupId(id);

        Long userId = userRepository.findByToken(token).get().getId();
        if(!userId.equals(group.getOwnerId())) {
            throw new UnauthorizedException("관리자에게만 권한이 있습니다.");
        }

        List<Goal> goals = goalRepository.findActiveRoutine(id);
        goals.forEach(g -> {
            if (g.getSuccessNum() >= groupMembers.size()/2) {
                goalRepository.updateSuccess(true, g.getUser().getId(), g.getGroup().getId());
            }
        });
        goals.forEach(g -> goalRepository.updateIsExpired(true, g.getUser().getId(), g.getGroup().getId()));
        //goals.forEach(g -> g.updateIsExpired());
        return true;
    }
}
