package com.ourjupiter.springboot.service.posts;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.posts.Posts;
import com.ourjupiter.springboot.web.dto.PostsListResponseDto;
//import com.ourjupiter.springboot.web.dto.PostsResponseDto;
import com.ourjupiter.springboot.domain.posts.PostsRepository;
import com.ourjupiter.springboot.web.dto.PostsResponseDto;
import com.ourjupiter.springboot.web.dto.PostsSaveRequestDto;
//import com.ourjupiter.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import com.ourjupiter.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public Long save(Long groupId, PostsSaveRequestDto requestDto) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id=" + groupId));

        requestDto.setGroup(group);

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getFileId());

        return id;
    }
    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllByGroupId(Long groupId) {

        return postsRepository.findAllByGroupId(groupId).stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
