package com.ourjupiter.springboot.web.dto;

import com.ourjupiter.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@NoArgsConstructor
public class JoinRequestDto {
    private String email;
    private String name;
    private String password;

    @Builder
    public JoinRequestDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
