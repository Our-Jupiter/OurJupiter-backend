package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.group.GroupService;
import com.ourjupiter.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class GroupController {
    private final GroupService groupService;

//    @CrossOrigin("*")
//    @GetMapping("/group")
//    public String getGroup(@RequestHeader("x-access-token") String token) {
//
//        return groupService.getGroup(token);
//    }

    @CrossOrigin("*")
    @PostMapping("/group")
    public String createGroup(@RequestBody GroupCreateRequestDto requestDto) {

        return groupService.createGroup(requestDto);
    }

    @CrossOrigin("*")
    @PutMapping("/group/{id}")
    public String update(@PathVariable Long id, @RequestBody GroupUpdateRequestDto requestDto) {

        return groupService.updateGroup(id, requestDto);
    }

    @CrossOrigin("*")
    @DeleteMapping("/group/{id}")
    public String delete(@PathVariable Long id) {

        return groupService.deleteGroup(id);
    }
}
