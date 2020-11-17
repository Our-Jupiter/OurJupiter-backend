package com.ourjupiter.springboot.service.user;

import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.web.dto.*;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private String key = "key";

    public String join(JoinRequestDto signUpRequest){
        verifyDuplicatedUser(signUpRequest.getEmail());

        userRepository.save(signUpRequest.toEntity());
        return "회원가입 성공";
    }

    private void verifyDuplicatedUser(String email){
        if(userRepository.findByEmail(email).isPresent()) {
            throw new UnauthorizedException("중복된 이메일 입니다");
        }
    }

    @Transactional
    public String login(LoginRequestDto signInRequestDto){
        User findUser = userRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("없는 유저입니다 ."));

        if(!BCrypt.checkpw(signInRequestDto.getPassword(), findUser.getPassword()))
            throw new UnauthorizedException("암호가 일치하지 않습니다 .");

        String token = createToken(findUser);
        findUser.setToken(token);

        return token;
    }

    public String createToken(User user){
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        int expiredTime = 1000 * 601;
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);
        payloads.put("exp", now);
        payloads.put("id", user.getId());
        payloads.put("email", user.getEmail());
        payloads.put("name", user.getName());

        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwt;
    }

    public Claims decodeToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(key.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("올바르지 않은 형식의 토큰입니다.");
        }
    }
}
