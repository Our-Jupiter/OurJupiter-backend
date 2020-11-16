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
    private String groupName;
    private String groupId;

    @Builder
    public MailDto(String groupId, String email, String groupName) {
        this.email = email;
        this.groupName = groupName;
        this.groupId = groupId;
    }
}
