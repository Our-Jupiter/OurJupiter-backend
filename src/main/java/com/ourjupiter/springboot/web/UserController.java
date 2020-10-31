package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.user.*;
import com.ourjupiter.springboot.web.dto.JoinRequestDto;
import com.ourjupiter.springboot.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @CrossOrigin("*")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto) {

        return userService.login(requestDto);
    }

    @CrossOrigin("*")
    @PostMapping("/join")
    public String join(@RequestBody JoinRequestDto requestDto) {

        userService.join(requestDto);
        return "회원가입 성공";
    }
}
