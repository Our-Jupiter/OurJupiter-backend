package com.ourjupiter.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String email;
    private Long groupId;
    private String groupName;

    @Builder
    public MailDto(String email, Long groupId, String groupName) {
        this.email = email;
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
