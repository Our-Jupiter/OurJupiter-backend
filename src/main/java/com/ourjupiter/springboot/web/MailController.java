package com.ourjupiter.springboot.web;

import lombok.RequiredArgsConstructor;
import com.ourjupiter.springboot.web.dto.*;
import com.ourjupiter.springboot.service.group.MailService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@RestController
public class MailController {
    private final MailService mailService;

    @CrossOrigin("*")
    @PostMapping("/mail")
    public String execMail(@RequestBody MailDto mailDto) throws UnsupportedEncodingException, MessagingException {

        mailService.sendMail(mailDto);
        return "Mail send success";
    }

    @CrossOrigin("*")
    @PostMapping("/invite")
    public String invite(@RequestBody InviteRequestDto inviteRequestDto) {

        mailService.inviteGroup(inviteRequestDto);
        return "그룹 초대 성공";
    }
}
