package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.user.*;
import com.ourjupiter.springboot.web.dto.JoinRequestDto;
import com.ourjupiter.springboot.web.dto.LoginRequestDto;
import io.jsonwebtoken.Claims;
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

        return userService.join(requestDto);
    }

    @CrossOrigin("*")
    @GetMapping("/me")
    public Claims me(@RequestHeader("x-access-token") String token) {

        return userService.decodeToken(token);
    }
}
