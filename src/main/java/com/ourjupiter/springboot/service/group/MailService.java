package com.ourjupiter.springboot.service.group;

import com.ourjupiter.springboot.domain.group.Group;
import com.ourjupiter.springboot.domain.group.GroupRepository;
import com.ourjupiter.springboot.domain.user.User;
import com.ourjupiter.springboot.domain.user.UserRepository;
import com.ourjupiter.springboot.domain.user_group.UserGroup;
import com.ourjupiter.springboot.domain.user_group.UserGroupRepository;
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
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void sendMail(MailDto mailDto) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(mailDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("가입되어있지 않은 유저입니다 ."));
        Long userId = user.getId();

        Group group = groupRepository.findById(mailDto.getGroupId()).get();
        Long groupId = group.getId();
        String groupName = group.getName();

        MailHandler mail = new MailHandler(javaMailSender);
        mail.setFrom(MailService.FROM_ADDRESS, "ourjupiter");
        mail.setTo(mailDto.getEmail());
        mail.setSubject("Ourjupiter : "+groupName+"그룹 초대 메일입니다");
        mail.setText(new StringBuffer().append("<h1>당신을 "+groupName+" 그룹에 초대합니다!</h1>")
                .append("<h3><p>하단의 링크를 클릭하면 "+groupName+" 그룹 가입화면으로 이동합니다.</p><br />" +
                        "로그인이 되어 있지 않다면 로그인 후 재접속 바랍니다.</h3>")
                .append("<h2><a href=\"http://localhost:8081/#/invite/"
                        + userId + "/" + groupId + "\">그룹 가입하러 가기</a></h2>")
                .toString()
        );
        mail.send();

        UserGroup newPair = UserGroup.builder()
                .user(user)
                .group(group)
                .joined(0)  // invited
                .build();
        userGroupRepository.save(newPair);
    }

    @Transactional
    public String inviteGroup(InviteRequestDto inviteRequestDto) {
        UserGroup userGroup = userGroupRepository.findByIds(inviteRequestDto.getUserId(), inviteRequestDto.getGroupId())
                .orElseThrow(() -> new UnauthorizedException("초대대상이 아닙니다."));
        if (userGroup.getJoined() == 1)
            throw new UnauthorizedException("이미 가입되어있습니다.");
        if (userGroup.getJoined() == 0) {
            userGroup.update(1);
        }
        return "가입되었습니다.";
    }
}
