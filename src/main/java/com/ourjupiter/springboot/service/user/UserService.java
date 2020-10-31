package com.ourjupiter.springboot.service.user;

import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public String join(JoinRequestDto signUpRequest){
        verifyDuplicatedUser(signUpRequest.getEmail());

        userRepository.save(signUpRequest.toEntity());
        return "회원가입 성공";
    }

    private void verifyDuplicatedUser(String email){
        if(userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일 입니다");
        }
    }

    @Transactional
    public String login(LoginRequestDto signInRequestDto){
        User findUser = userRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다 ."));

        if(!BCrypt.checkpw(signInRequestDto.getPassword(), findUser.getPassword()))
            throw new IllegalArgumentException("암호가 일치하지 않습니다 .");

        return "로그인 성공, 토큰 반환";
    }
}
