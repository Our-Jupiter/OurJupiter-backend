package com.ourjupiter.springboot.service.group;

import lombok.RequiredArgsConstructor;
import com.ourjupiter.springboot.web.dto.*;
import com.ourjupiter.springboot.web.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service

public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "OurJupiter";

    @Transactional
    public void sendMail(MailDto mailDto) throws MessagingException, UnsupportedEncodingException {

        MailDto.builder()
                .email(mailDto.getEmail())
                .groupId(mailDto.getGroupId())
                .groupName(mailDto.getGroupName())
                .build();

        MailHandler mail = new MailHandler(javaMailSender);
        mail.setFrom(MailService.FROM_ADDRESS, "ourjupiter");
        mail.setTo(mailDto.getEmail());
        mail.setSubject("Ourjupiter : "+mailDto.getGroupName()+"그룹 초대 메일입니다");
        mail.setText(new StringBuffer().append("<h1>당신을 "+mailDto.getGroupName()+" 그룹에 초대합니다!</h1>")
                .append("<h3><p>하단의 링크를 클릭하면 "+mailDto.getGroupName()+" 그룹 가입화면으로 이동합니다.</p></h3>")
                .append("<h2><a href=\"http://localhost:8081/#/home\">그룹 가입하러 가기</a></h2>") //여기에 그룹 가입하는 링크 들어가야 함!
                .toString()
        );
        mail.send();


    }
}
