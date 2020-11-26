package com.ourjupiter.springboot.service.group;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
import com.ourjupiter.springboot.web.dto.*;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public List<Pair<Long, String>> getGroup(String token){
        User user = userRepository.findByToken(token).get();

        List<UserGroup> userGroup = userGroupRepository.findJoinedGroupByUserId(user.getId());

        List<Pair<Long, String>> groupList = new ArrayList<Pair<Long, String>> ();
        userGroup.forEach(g -> groupList.add(new Pair<>(g.getGroup().getId(), g.getGroup().getName())));

        return groupList;
    }

    @Transactional
    public String getOwnerEmail(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        String ownerEmail = userRepository.findById(group.getOwnerId()).get().getEmail();
        return ownerEmail;
    }

    @Transactional
    public String createGroup(String token, GroupCreateRequestDto groupCreateRequest){

        User user = userRepository.findByToken(token).get();
        Long ownerId = user.getId();
        Group newGroup = Group.builder()
                .name(groupCreateRequest.getName())
                .ownerId(ownerId)
                .build();

        groupRepository.save(newGroup);

        UserGroup newPair = UserGroup.builder()
                .user(user)
                .group(newGroup)
                .joined(1)
                .build();

        userGroupRepository.save(newPair);

        return "그룹 생성 성공";
    }

    @Transactional
    public String updateGroup(Long id, String token, GroupUpdateRequestDto requestDto){

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        Long userId = userRepository.findByToken(token).get().getId();
        if(!userId.equals(group.getOwnerId())) {
            throw new UnauthorizedException("관리자에게만 권한이 있습니다.");
        }

        group.update(requestDto.getName());
        return "그룹 수정 성공";
    }

    @Transactional
    public String deleteGroup(Long id, String token){

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        Long userId = userRepository.findByToken(token).get().getId();
        if(!userId.equals(group.getOwnerId())) {
            throw new UnauthorizedException("관리자에게만 권한이 있습니다.");
        }
        groupRepository.delete(group);
        return "그룹 삭제 성공";
    }
}
