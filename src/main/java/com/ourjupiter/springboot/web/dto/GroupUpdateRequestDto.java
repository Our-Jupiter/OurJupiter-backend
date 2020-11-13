package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupUpdateRequestDto {
    private String name;

    @Builder
    public GroupUpdateRequestDto(String name) {
        this.name = name;
    }
}
