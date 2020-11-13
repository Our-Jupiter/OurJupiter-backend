package com.ourjupiter.springboot.service.group;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.web.dto.GroupCreateRequestDto;
import com.ourjupiter.springboot.web.dto.GroupUpdateRequestDto;
import com.ourjupiter.springboot.web.dto.UnauthorizedException;
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

    @Transactional
    public List<Pair<Long, String>> getGroup(String token){
        User user = userRepository.findByToken(token).get();

        List<Pair<Long, String>> groupList = new ArrayList<Pair<Long, String>> ();
        user.getGroup().forEach(g -> groupList.add(new Pair<>(g.getId(), g.getName())));

        return groupList;
    }

    @Transactional
    public String createGroup(String token, GroupCreateRequestDto groupCreateRequest){

        Long ownerId = userRepository.findByToken(token).get().getId();
        Group newGroup = Group.builder()
                .name(groupCreateRequest.getName())
                .ownerId(ownerId)
                .build();

        groupRepository.save(newGroup);

        userRepository.findByToken(token).get().getGroup().add(newGroup);

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

        group.getUser().forEach(u -> u.getGroup().remove(group));
        userRepository.saveAll(group.getUser());

        groupRepository.delete(group);
        return "그룹 삭제 성공";
    }
}
