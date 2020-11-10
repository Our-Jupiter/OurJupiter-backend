package com.ourjupiter.springboot.service.group;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.web.dto.GroupCreateRequestDto;
import com.ourjupiter.springboot.web.dto.GroupUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public String getGroup(String token){

//        Long ownerId = userRepository.findByToken(groupCreateRequest.getToken()).get().getId();
//        Group newGroup = Group.builder()
//                .name(groupCreateRequest.getName())
//                .ownerId(ownerId)
//                .build();
//
//        groupRepository.save(newGroup);
        return "그룹 불러오기 성공";
    }

    @Transactional
    public String createGroup(GroupCreateRequestDto groupCreateRequest){

        Long ownerId = userRepository.findByToken(groupCreateRequest.getToken()).get().getId();
        Group newGroup = Group.builder()
                .name(groupCreateRequest.getName())
                .ownerId(ownerId)
                .build();

        groupRepository.save(newGroup);
        return "그룹 생성 성공";
    }

    @Transactional
    public String updateGroup(Long id, GroupUpdateRequestDto requestDto){

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        group.update(requestDto.getName());
        return "그룹 수정 성공";
    }

    @Transactional
    public String deleteGroup(Long id){

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + id));

        groupRepository.delete(group);
        return "그룹 생성 성공";
    }
}
