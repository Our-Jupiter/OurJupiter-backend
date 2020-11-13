package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupCreateRequestDto {
    private String name;

    @Builder
    public GroupCreateRequestDto(String name) {
        this.name = name;
    }
}
