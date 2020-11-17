package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteRequestDto {
    private Long userId;
    private Long groupId;

    @Builder
    public InviteRequestDto(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
