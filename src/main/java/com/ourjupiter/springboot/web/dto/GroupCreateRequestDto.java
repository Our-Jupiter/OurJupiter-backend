package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupCreateRequestDto {
    private String name;
    private String token;

    @Builder
    public GroupCreateRequestDto(String name, String token) {
        this.name = name;
        this.token  = token;
    }
}
